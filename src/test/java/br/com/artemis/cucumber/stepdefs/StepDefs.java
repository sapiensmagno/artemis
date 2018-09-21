package br.com.artemis.cucumber.stepdefs;

import br.com.artemis.ArtemisApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ArtemisApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
