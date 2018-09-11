package com.example.JIO.SolrSearch.Modals;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.apache.zookeeper.proto.ErrorResponse;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.ArrayList;
import java.util.List;


@Data
@SolrDocument(solrCoreName = "Employee_data")

public class Employee {


    @Id
    @Indexed(name = "id" , type = "string")
    private String id;                       //required

    @Indexed(name = "firstName" , type = "string")
    private String firstName;                   //required

    @Indexed(name = "lastName" , type = "string")
    private String lastName;                     //required

    @Indexed(name = "emailId" , type = "string")
    private String emailId;                       //required

    @Indexed(name = "phoneNo" , type = "string")
    private long phoneNo;                          //optional

    @Indexed(name = "officeCircle" , type = "string")
    private String officeCirlce;                   //optional

    @Indexed(name = "officeCity" , type = "string")
    private String officeCity;                    //optional


    private List<Skills> skills;                  //required

    @Indexed
    private String flattenedSkills;

    @Indexed
    private List<String> suggestion;

    @Indexed
    List<String> skillsString;

    private Employee(MyBuilder myBuilder)
    {
        this.firstName = myBuilder.firstName;
        this.lastName = myBuilder.lastName;
        this.emailId = myBuilder.emailId;
        this.id = myBuilder.id;
        this.phoneNo = myBuilder.phoneNo;
        this.officeCirlce = myBuilder.officeCirle;
        this.officeCity = myBuilder.officeCity;
        this.skills = myBuilder.skills;
        this.flattenedSkills = myBuilder.flattenedSkills;
        this.skillsString = myBuilder.skillsString;

    }



    public static class MyBuilder{

        private String id;
        private String firstName;
        private String lastName;
        private String emailId;
        private long phoneNo;
        private String officeCirle;
        private String officeCity;
        private List<Skills> skills;
        private String flattenedSkills;
        private List<String> skillsString;

        public MyBuilder id(String id)
        {
            this.id = id;
            return this;
        }
        public MyBuilder firstName(String firstName)
        {
            this.firstName = firstName;
            return this;
        }

        public MyBuilder lastName(String lastName)
        {
            this.lastName = lastName;
            return this;
        }
        public MyBuilder emailId(String emailId)
        {

            this.emailId = emailId;
            return this;
        }

        public  MyBuilder phoneNo(long phoneNo)
        {
            this.phoneNo = phoneNo;
            return this;
        }

        public MyBuilder getOfficeCirle(String officeCirle)
        {
            this.officeCirle = officeCirle;
            return this;
        }

        public MyBuilder skills(List<Skills> skills)
        {
            this.skills = skills;
            return  this;
        }

        public void setFlattenedSkills(List<Skills> skills)
        {
            StringBuffer flattenedSkills = new StringBuffer();

            for(Skills s : skills)
            {
                flattenedSkills.append(s.getSkill_name()+" "+s.getSkill_level()).append(" ");
            }

            this.flattenedSkills = flattenedSkills.toString();
        }

        // process method can only be apply after skills(List<Skills> skills) method
        public MyBuilder process()
        {
            setFlattenedSkills(this.skills);
            skillsString = new ArrayList<>();

            for(int i=0 ; i < skills.size() ; i++)
            {
                this.skillsString.add(this.skills.get(i).getSkill_name() + " "  + this.skills.get(i).getSkill_level());
            }

            return this;
        }

        //use build after process()
        public Employee mybuild()
        {
            return new Employee(this);
        }


    }








}
