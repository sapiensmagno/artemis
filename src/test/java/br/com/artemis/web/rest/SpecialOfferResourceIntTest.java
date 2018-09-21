package br.com.artemis.web.rest;

import br.com.artemis.ArtemisApp;

import br.com.artemis.domain.SpecialOffer;
import br.com.artemis.repository.SpecialOfferRepository;
import br.com.artemis.service.SpecialOfferService;
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
import java.math.BigDecimal;
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
 * Test class for the SpecialOfferResource REST controller.
 *
 * @see SpecialOfferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtemisApp.class)
public class SpecialOfferResourceIntTest {

    private static final BigDecimal DEFAULT_DISCOUNT_PERCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT_PERCENT = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SpecialOfferRepository specialOfferRepository;

    @Autowired
    private SpecialOfferService specialOfferService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSpecialOfferMockMvc;

    private SpecialOffer specialOffer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecialOfferResource specialOfferResource = new SpecialOfferResource(specialOfferService);
        this.restSpecialOfferMockMvc = MockMvcBuilders.standaloneSetup(specialOfferResource)
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
    public static SpecialOffer createEntity(EntityManager em) {
        SpecialOffer specialOffer = new SpecialOffer()
            .discountPercent(DEFAULT_DISCOUNT_PERCENT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return specialOffer;
    }

    @Before
    public void initTest() {
        specialOffer = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialOffer() throws Exception {
        int databaseSizeBeforeCreate = specialOfferRepository.findAll().size();

        // Create the SpecialOffer
        restSpecialOfferMockMvc.perform(post("/api/special-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialOffer)))
            .andExpect(status().isCreated());

        // Validate the SpecialOffer in the database
        List<SpecialOffer> specialOfferList = specialOfferRepository.findAll();
        assertThat(specialOfferList).hasSize(databaseSizeBeforeCreate + 1);
        SpecialOffer testSpecialOffer = specialOfferList.get(specialOfferList.size() - 1);
        assertThat(testSpecialOffer.getDiscountPercent()).isEqualTo(DEFAULT_DISCOUNT_PERCENT);
        assertThat(testSpecialOffer.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSpecialOffer.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createSpecialOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialOfferRepository.findAll().size();

        // Create the SpecialOffer with an existing ID
        specialOffer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialOfferMockMvc.perform(post("/api/special-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialOffer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SpecialOffer> specialOfferList = specialOfferRepository.findAll();
        assertThat(specialOfferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDiscountPercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialOfferRepository.findAll().size();
        // set the field null
        specialOffer.setDiscountPercent(null);

        // Create the SpecialOffer, which fails.

        restSpecialOfferMockMvc.perform(post("/api/special-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialOffer)))
            .andExpect(status().isBadRequest());

        List<SpecialOffer> specialOfferList = specialOfferRepository.findAll();
        assertThat(specialOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialOfferRepository.findAll().size();
        // set the field null
        specialOffer.setStartDate(null);

        // Create the SpecialOffer, which fails.

        restSpecialOfferMockMvc.perform(post("/api/special-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialOffer)))
            .andExpect(status().isBadRequest());

        List<SpecialOffer> specialOfferList = specialOfferRepository.findAll();
        assertThat(specialOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialOfferRepository.findAll().size();
        // set the field null
        specialOffer.setEndDate(null);

        // Create the SpecialOffer, which fails.

        restSpecialOfferMockMvc.perform(post("/api/special-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialOffer)))
            .andExpect(status().isBadRequest());

        List<SpecialOffer> specialOfferList = specialOfferRepository.findAll();
        assertThat(specialOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpecialOffers() throws Exception {
        // Initialize the database
        specialOfferRepository.saveAndFlush(specialOffer);

        // Get all the specialOfferList
        restSpecialOfferMockMvc.perform(get("/api/special-offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].discountPercent").value(hasItem(DEFAULT_DISCOUNT_PERCENT.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }

    @Test
    @Transactional
    public void getSpecialOffer() throws Exception {
        // Initialize the database
        specialOfferRepository.saveAndFlush(specialOffer);

        // Get the specialOffer
        restSpecialOfferMockMvc.perform(get("/api/special-offers/{id}", specialOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(specialOffer.getId().intValue()))
            .andExpect(jsonPath("$.discountPercent").value(DEFAULT_DISCOUNT_PERCENT.intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingSpecialOffer() throws Exception {
        // Get the specialOffer
        restSpecialOfferMockMvc.perform(get("/api/special-offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialOffer() throws Exception {
        // Initialize the database
        specialOfferService.save(specialOffer);

        int databaseSizeBeforeUpdate = specialOfferRepository.findAll().size();

        // Update the specialOffer
        SpecialOffer updatedSpecialOffer = specialOfferRepository.findOne(specialOffer.getId());
        updatedSpecialOffer
            .discountPercent(UPDATED_DISCOUNT_PERCENT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restSpecialOfferMockMvc.perform(put("/api/special-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpecialOffer)))
            .andExpect(status().isOk());

        // Validate the SpecialOffer in the database
        List<SpecialOffer> specialOfferList = specialOfferRepository.findAll();
        assertThat(specialOfferList).hasSize(databaseSizeBeforeUpdate);
        SpecialOffer testSpecialOffer = specialOfferList.get(specialOfferList.size() - 1);
        assertThat(testSpecialOffer.getDiscountPercent()).isEqualTo(UPDATED_DISCOUNT_PERCENT);
        assertThat(testSpecialOffer.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSpecialOffer.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialOffer() throws Exception {
        int databaseSizeBeforeUpdate = specialOfferRepository.findAll().size();

        // Create the SpecialOffer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSpecialOfferMockMvc.perform(put("/api/special-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(specialOffer)))
            .andExpect(status().isCreated());

        // Validate the SpecialOffer in the database
        List<SpecialOffer> specialOfferList = specialOfferRepository.findAll();
        assertThat(specialOfferList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSpecialOffer() throws Exception {
        // Initialize the database
        specialOfferService.save(specialOffer);

        int databaseSizeBeforeDelete = specialOfferRepository.findAll().size();

        // Get the specialOffer
        restSpecialOfferMockMvc.perform(delete("/api/special-offers/{id}", specialOffer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SpecialOffer> specialOfferList = specialOfferRepository.findAll();
        assertThat(specialOfferList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialOffer.class);
        SpecialOffer specialOffer1 = new SpecialOffer();
        specialOffer1.setId(1L);
        SpecialOffer specialOffer2 = new SpecialOffer();
        specialOffer2.setId(specialOffer1.getId());
        assertThat(specialOffer1).isEqualTo(specialOffer2);
        specialOffer2.setId(2L);
        assertThat(specialOffer1).isNotEqualTo(specialOffer2);
        specialOffer1.setId(null);
        assertThat(specialOffer1).isNotEqualTo(specialOffer2);
    }
}
