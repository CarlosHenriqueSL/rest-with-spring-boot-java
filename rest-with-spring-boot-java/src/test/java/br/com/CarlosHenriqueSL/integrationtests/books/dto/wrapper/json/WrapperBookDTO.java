package br.com.CarlosHenriqueSL.integrationtests.books.dto.wrapper.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperBookDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private BookEmbeddedDTO emmbedded;

    public WrapperBookDTO() {}

    public BookEmbeddedDTO getEmmbedded() {
        return emmbedded;
    }

    public void setEmmbedded(BookEmbeddedDTO emmbedded) {
        this.emmbedded = emmbedded;
    }
}
