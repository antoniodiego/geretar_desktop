/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.Test;

/**
 *
 * @author Antônoio Diego <antoniodiegoluz at gmail.com>
 */
public class TesteCData {
    
    @Test
    public void testaC(){
          LocalDateTime dataHL =LocalDateTime.now().withHour(15);
            Instant ins = dataHL.toInstant(ZoneOffset.of("-3"));
            System.out.println("D");
    }
}
