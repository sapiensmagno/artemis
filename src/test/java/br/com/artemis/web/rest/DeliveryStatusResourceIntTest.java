package br.com.artemis.web.rest;

import br.com.artemis.ArtemisApp;

import br.com.artemis.domain.DeliveryStatus;
import br.com.artemis.repository.DeliveryStatusRepository;
import br.com.artemis.service.DeliveryStatusService;
import br.com.artemis.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static br.com.artemis.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DeliveryStatusResource REST controller.
 *
 * @see DeliveryStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtemisApp.class)
public class DeliveryStatusResourceIntTest {

    private static final ZonedDateTime DEFAULT_LAST_UPDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DeliveryStatusRepository deliveryStatusRepository;

    @Autowired
    private DeliveryStatusService deliveryStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeliveryStatusMockMvc;

    private DeliveryStatus deliveryStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryStatusResource deliveryStatusResource = new DeliveryStatusResource(deliveryStatusService);
        this.restDeliveryStatusMockMvc = MockMvcBuilders.standaloneSetup(deliveryStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryStatus createEntity(EntityManager em) {
        DeliveryStatus deliveryStatus = new DeliveryStatus()
            .lastUpdate(DEFAULT_LAST_UPDATE)
            .name(DEFAULT_NAME);
        return deliveryStatus;
    }

    @Before
    public void initTest() {
        deliveryStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryStatus() throws Exception {
        int databaseSizeBeforeCreate = deliveryStatusRepository.findAll().size();

        // Create the DeliveryStatus
        restDeliveryStatusMockMvc.perform(post("/api/delivery-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryStatus)))
            .andExpect(status().isCreated());

        // Validate the DeliveryStatus in the database
        List<DeliveryStatus> deliveryStatusList = deliveryStatusRepository.findAll();
        assertThat(deliveryStatusList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryStatus testDeliveryStatus = deliveryStatusList.get(deliveryStatusList.size() - 1);
        assertThat(testDeliveryStatus.getLastUpdate()).isEqualTo(DEFAULT_LAST_UPDATE);
        assertThat(testDeliveryStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDeliveryStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryStatusRepository.findAll().size();

        // Create the DeliveryStatus with an existing ID
        deliveryStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryStatusMockMvc.perform(post("/api/delivery-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryStatus)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DeliveryStatus> deliveryStatusList = deliveryStatusRepository.findAll();
        assertThat(deliveryStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeliveryStatuses() throws Exception {
        // Initialize the database
        deliveryStatusRepository.saveAndFlush(deliveryStatus);

        // Get all the deliveryStatusList
        restDeliveryStatusMockMvc.perform(get("/api/delivery-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastUpdate").value(hasItem(sameInstant(DEFAULT_LAST_UPDATE))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDeliveryStatus() throws Exception {
        // Initialize the database
        deliveryStatusRepository.saveAndFlush(deliveryStatus);

        // Get the deliveryStatus
        restDeliveryStatusMockMvc.perform(get("/api/delivery-statuses/{id}", deliveryStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryStatus.getId().intValue()))
            .andExpect(jsonPath("$.lastUpdate").value(sameInstant(DEFAULT_LAST_UPDATE)))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveryStatus() throws Exception {
        // Get the deliveryStatus
        restDeliveryStatusMockMvc.perform(get("/api/delivery-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryStatus() throws Exception {
        // Initialize the database
        deliveryStatusService.save(deliveryStatus);

        int databaseSizeBeforeUpdate = deliveryStatusRepository.findAll().size();

        // Update the deliveryStatus
        DeliveryStatus updatedDeliveryStatus = deliveryStatusRepository.findOne(deliveryStatus.getId());
        updatedDeliveryStatus
            .lastUpdate(UPDATED_LAST_UPDATE)
            .name(UPDATED_NAME);

        restDeliveryStatusMockMvc.perform(put("/api/delivery-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeliveryStatus)))
            .andExpect(status().isOk());

        // Validate the DeliveryStatus in the database
        List<DeliveryStatus> deliveryStatusList = deliveryStatusRepository.findAll();
        assertThat(deliveryStatusList).hasSize(databaseSizeBeforeUpdate);
        DeliveryStatus testDeliveryStatus = deliveryStatusList.get(deliveryStatusList.size() - 1);
        assertThat(testDeliveryStatus.getLastUpdate()).isEqualTo(UPDATED_LAST_UPDATE);
        assertThat(testDeliveryStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryStatus() throws Exception {
        int databaseSizeBeforeUpdate = deliveryStatusRepository.findAll().size();

        // Create the DeliveryStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeliveryStatusMockMvc.perform(put("/api/delivery-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deliveryStatus)))
            .andExpect(status().isCreated());

        // Validate the DeliveryStatus in the database
        List<DeliveryStatus> deliveryStatusList = deliveryStatusRepository.findAll();
        assertThat(deliveryStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeliveryStatus() throws Exception {
        // Initialize the database
        deliveryStatusService.save(deliveryStatus);

        int databaseSizeBeforeDelete = deliveryStatusRepository.findAll().size();

        // Get the deliveryStatus
        restDeliveryStatusMockMvc.perform(delete("/api/delivery-statuses/{id}", deliveryStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeliveryStatus> deliveryStatusList = deliveryStatusRepository.findAll();
        assertThat(deliveryStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeliveryStatus.class);
        DeliveryStatus deliveryStatus1 = new DeliveryStatus();
        deliveryStatus1.setId(1L);
        DeliveryStatus deliveryStatus2 = new DeliveryStatus();
        deliveryStatus2.setId(deliveryStatus1.getId());
        assertThat(deliveryStatus1).isEqualTo(deliveryStatus2);
        deliveryStatus2.setId(2L);
        assertThat(deliveryStatus1).isNotEqualTo(deliveryStatus2);
        deliveryStatus1.setId(null);
        assertThat(deliveryStatus1).isNotEqualTo(deliveryStatus2);
    }
}
