package br.com.artemis.web.rest;

import br.com.artemis.ArtemisApp;

import br.com.artemis.domain.PriceRule;
import br.com.artemis.repository.PriceRuleRepository;
import br.com.artemis.service.PriceRuleService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PriceRuleResource REST controller.
 *
 * @see PriceRuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtemisApp.class)
public class PriceRuleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PriceRuleRepository priceRuleRepository;

    @Autowired
    private PriceRuleService priceRuleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPriceRuleMockMvc;

    private PriceRule priceRule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriceRuleResource priceRuleResource = new PriceRuleResource(priceRuleService);
        this.restPriceRuleMockMvc = MockMvcBuilders.standaloneSetup(priceRuleResource)
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
    public static PriceRule createEntity(EntityManager em) {
        PriceRule priceRule = new PriceRule()
            .name(DEFAULT_NAME);
        return priceRule;
    }

    @Before
    public void initTest() {
        priceRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriceRule() throws Exception {
        int databaseSizeBeforeCreate = priceRuleRepository.findAll().size();

        // Create the PriceRule
        restPriceRuleMockMvc.perform(post("/api/price-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceRule)))
            .andExpect(status().isCreated());

        // Validate the PriceRule in the database
        List<PriceRule> priceRuleList = priceRuleRepository.findAll();
        assertThat(priceRuleList).hasSize(databaseSizeBeforeCreate + 1);
        PriceRule testPriceRule = priceRuleList.get(priceRuleList.size() - 1);
        assertThat(testPriceRule.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPriceRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceRuleRepository.findAll().size();

        // Create the PriceRule with an existing ID
        priceRule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceRuleMockMvc.perform(post("/api/price-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceRule)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PriceRule> priceRuleList = priceRuleRepository.findAll();
        assertThat(priceRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceRuleRepository.findAll().size();
        // set the field null
        priceRule.setName(null);

        // Create the PriceRule, which fails.

        restPriceRuleMockMvc.perform(post("/api/price-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceRule)))
            .andExpect(status().isBadRequest());

        List<PriceRule> priceRuleList = priceRuleRepository.findAll();
        assertThat(priceRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceRules() throws Exception {
        // Initialize the database
        priceRuleRepository.saveAndFlush(priceRule);

        // Get all the priceRuleList
        restPriceRuleMockMvc.perform(get("/api/price-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPriceRule() throws Exception {
        // Initialize the database
        priceRuleRepository.saveAndFlush(priceRule);

        // Get the priceRule
        restPriceRuleMockMvc.perform(get("/api/price-rules/{id}", priceRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priceRule.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPriceRule() throws Exception {
        // Get the priceRule
        restPriceRuleMockMvc.perform(get("/api/price-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceRule() throws Exception {
        // Initialize the database
        priceRuleService.save(priceRule);

        int databaseSizeBeforeUpdate = priceRuleRepository.findAll().size();

        // Update the priceRule
        PriceRule updatedPriceRule = priceRuleRepository.findOne(priceRule.getId());
        updatedPriceRule
            .name(UPDATED_NAME);

        restPriceRuleMockMvc.perform(put("/api/price-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPriceRule)))
            .andExpect(status().isOk());

        // Validate the PriceRule in the database
        List<PriceRule> priceRuleList = priceRuleRepository.findAll();
        assertThat(priceRuleList).hasSize(databaseSizeBeforeUpdate);
        PriceRule testPriceRule = priceRuleList.get(priceRuleList.size() - 1);
        assertThat(testPriceRule.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceRule() throws Exception {
        int databaseSizeBeforeUpdate = priceRuleRepository.findAll().size();

        // Create the PriceRule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPriceRuleMockMvc.perform(put("/api/price-rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceRule)))
            .andExpect(status().isCreated());

        // Validate the PriceRule in the database
        List<PriceRule> priceRuleList = priceRuleRepository.findAll();
        assertThat(priceRuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePriceRule() throws Exception {
        // Initialize the database
        priceRuleService.save(priceRule);

        int databaseSizeBeforeDelete = priceRuleRepository.findAll().size();

        // Get the priceRule
        restPriceRuleMockMvc.perform(delete("/api/price-rules/{id}", priceRule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PriceRule> priceRuleList = priceRuleRepository.findAll();
        assertThat(priceRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceRule.class);
        PriceRule priceRule1 = new PriceRule();
        priceRule1.setId(1L);
        PriceRule priceRule2 = new PriceRule();
        priceRule2.setId(priceRule1.getId());
        assertThat(priceRule1).isEqualTo(priceRule2);
        priceRule2.setId(2L);
        assertThat(priceRule1).isNotEqualTo(priceRule2);
        priceRule1.setId(null);
        assertThat(priceRule1).isNotEqualTo(priceRule2);
    }
}
