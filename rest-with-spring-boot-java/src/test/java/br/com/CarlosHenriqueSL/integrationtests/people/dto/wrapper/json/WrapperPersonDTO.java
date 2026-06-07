package br.com.CarlosHenriqueSL.integrationtests.people.dto.wrapper.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperPersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private PersonEmbeddedDTO emmbedded;

    public WrapperPersonDTO() {}

    public PersonEmbeddedDTO getEmmbedded() {
        return emmbedded;
    }

    public void setEmmbedded(PersonEmbeddedDTO emmbedded) {
        this.emmbedded = emmbedded;
    }
}
