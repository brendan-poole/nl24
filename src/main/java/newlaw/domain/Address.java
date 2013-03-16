//TODO: Don't use embedded classes as they don't get validated
package newlaw.domain;


import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@Embeddable
public class Address {

    private String houseName;

    private String houseNumber;

    private String postcode;

    private String street1;

    private String street2;

    private String district;

    private String city;

    private String county;

    private String country;

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (houseName != null) {
            sb.append(houseName).append(", ");
        }
        if (houseNumber != null) {
            sb.append(houseNumber).append(" ");
        }
        if (street1 != null) {
            sb.append(street1).append(", ");
        }
        if(street2 != null) {
        	sb.append(street2).append(", ");
        }
        if(district != null) {
        	sb.append(district).append(", ");
        }
        if(city != null) {
        	sb.append(city).append(", ");
        }
        if(county != null) {
        	sb.append(county).append(", ");
        }
        if(postcode != null) {
        	sb.append(postcode);
        }
        
        return sb.toString().replaceFirst(" ,*$", "");
    }
}
