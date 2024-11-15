package it.palex.provaLogin.web.service;

import it.palex.provaLogin.dbaccess.entities.UsersAuthDetails;
import it.palex.provaLogin.dbservices.service.UsersAuthDetailsService;
import it.palex.provaLogin.library.exceptions.UnauthorizedException;
import it.palex.provaLogin.web.soap.GetProfileResponse;
import it.palex.provaLogin.web.soap.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class UserProfileWebService {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserProfileWebService.class);

    @Autowired
    private UsersAuthDetailsService usersAuthDetailsService;


    public GetProfileResponse getCurrentLoggedUserProfile() {
        if(SecurityContextHolder.getContext()==null || SecurityContextHolder.getContext().getAuthentication()==null
                 || SecurityContextHolder.getContext().getAuthentication().getName()==null){
            throw new UnauthorizedException();
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UsersAuthDetails user = this.usersAuthDetailsService.findByUsername(username);

        if(user==null){
            throw new UnauthorizedException("User not found");
        }

        GetProfileResponse res = new GetProfileResponse();

        Profile profile = new Profile();
        profile.setId(user.getId().intValue());
        profile.setName(user.getName());
        profile.setSurname(user.getSurname());
        XMLGregorianCalendar registrationDate = this.dateToGregorianCalendar(user.getCreatedDate());
        profile.setRegistrationDate(registrationDate);

        res.setProfile(profile);

        return res;
    }

    private XMLGregorianCalendar dateToGregorianCalendar(Date date){
        if(date==null){
            return null;
        }

        XMLGregorianCalendar xmlDate = null;
        GregorianCalendar gc = new GregorianCalendar();

        // giving current date and time to gc
        gc.setTime(date);

        try {
            xmlDate = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gc);

            return xmlDate;
        }
        catch (Throwable e) {
            LOGGER.error("Error converting date to XMLGregorianCalendar", e);

            return null;
        }
    }
}
