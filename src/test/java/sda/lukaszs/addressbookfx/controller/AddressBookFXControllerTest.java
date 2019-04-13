package sda.lukaszs.addressbookfx.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class AddressBookFXControllerTest {

    @ParameterizedTest
    @CsvSource({"sad_frog.jpg,jpg","reeeeee.mp3,mp3","wtf.png.avi,avi"})
    void getFileExtension(String fileName, String expectedResult) {
        assertThat(AddressBookFXController.getFileExtension(fileName)).isEqualTo(expectedResult);
    }
}