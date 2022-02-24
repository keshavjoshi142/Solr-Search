package com.example.JIO.SolrSearch.Models.Employee;


import com.example.JIO.SolrSearch.Models.Skills;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.ArrayList;
import java.util.List;


@Data
@SolrDocument(collection = "Employee_data")

public class Employee {


    @Id
    @Indexed(name = "id" , type = "Long")
    private Long id;

    @Indexed(name = "name" , type = "string")
    private String name;

    @Indexed(name = "userName" , type = "string")
    private String userName;

    @Indexed(name = "emailId" , type = "string")
    private String emailId;

    @Indexed(name = "phoneNo" , type = "string")
    private String phoneNo;

    @Indexed
    private List<String> skills;

    @Indexed
    private List<String> instName;

    @Indexed
    private List<String> degree;

    @Indexed
    private List<String> eduFrom;

    @Indexed
    private List<String> eduTo;

    @Indexed
    private List<String> projectName;

    @Indexed
    private List<String> projFrom;

    @Indexed
    private List<String> projTo;

    @Indexed
    private List<String> description;

    @Indexed(name = "image" , type = "string")
    private String image;

    @Indexed(name = "weight" , type = "Long")
    private Long weight;

    @Indexed
    private String flattenedSkills;

    @Indexed
    private List<String> suggestion;



    @Indexed
    private List<String> SkillsSet;

    private Employee(MyBuilder myBuilder)
    {
        this.id = myBuilder.id;
        this.name = myBuilder.name;
        this.userName = myBuilder.userName;
        this.emailId = myBuilder.emailId;
        this.phoneNo = myBuilder.phoneNo;
        this.skills = myBuilder.skills;
        this.instName = myBuilder.instName;
        this.degree = myBuilder.degree;
        this.eduFrom = myBuilder.eduFrom;
        this.eduTo = myBuilder.eduTo;
        this.projectName = myBuilder.projectName;
        this.projFrom = myBuilder.projFrom;
        this.projTo = myBuilder.projTo;
        this.description = myBuilder.description;
        this.image = myBuilder.image;
        this.flattenedSkills = myBuilder.flattenedSkills;
        this.weight = myBuilder.weight;

    }



    public static class MyBuilder{

        private Long id;
        private String name;
        private String userName;
        private String emailId;
        private String phoneNo;
        private List<String> skills;
        private List<String> instName;
        private List<String> degree;
        private List<String> eduFrom;
        private List<String> eduTo;
        private List<String> projectName;
        private List<String> projFrom;
        private List<String> projTo;
        private List<String> description;
        private String image;
        private Long weight;
        private String flattenedSkills;
        private  List<String> suggestion;

        public MyBuilder id(Long id)
        {
            this.id = id;
            return this;
        }
        public MyBuilder name(String name)
        {
            this.name = name;
            return this;
        }

        public MyBuilder userName(String userName)
        {
            this.userName = userName;
            return this;
        }
        public MyBuilder emailId(String emailId)
        {

            this.emailId = emailId;
            return this;
        }

        public  MyBuilder phoneNo(String phoneNo)
        {
            this.phoneNo = phoneNo;
            return this;
        }


        public MyBuilder skills(List<String> skills)
        {
            this.skills = skills;
            return  this;
        }

        public MyBuilder instName(List<String> instName)
        {
            this.instName = instName;
            return  this;
        }

        public MyBuilder degree(List<String> degree)
        {
            this.degree = degree;
            return  this;
        }

        public MyBuilder eduFrom(List<String> eduFrom)
        {
            this.eduFrom = eduFrom;
            return  this;
        }

        public MyBuilder eduTo(List<String> eduTo)
        {
            this.eduTo = eduTo;
            return  this;
        }

        public MyBuilder projectName(List<String> projectName)
        {
            this.projectName = projectName;
            return  this;
        }

        public MyBuilder projFrom(List<String> projFrom)
        {
            this.projFrom = projFrom;
            return  this;
        }

        public MyBuilder projTo(List<String> projTo)
        {
            this.projTo = projTo;
            return  this;
        }

        public MyBuilder description(List<String> description)
        {
            this.description = description;
            return  this;
        }

        public MyBuilder image(String image)
        {
            this.image = image;
            return  this;
        }

        public MyBuilder weight(Long weight) {
            this.weight = weight;
            return this;
        }

        public MyBuilder suggestion()
        {
            suggestion = new ArrayList<>();
            this.suggestion.add("ADVANCED");
            this.suggestion.add("BEGINNER");
            this.suggestion.add("INTERMEDIATE");

            return this;
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
      /*  public MyBuilder process()
        {
            setFlattenedSkills(this.skills);
            skillsString = new ArrayList<>();

            for(int i=0 ; i < skills.size() ; i++)
            {
                this.skillsString.add(this.skills.get(i).getSkill_name() + " "  + this.skills.get(i).getSkill_level());
            }

            return this;
        }*/

        //use build after process()
        public Employee mybuild()
        {
            return new Employee(this);
        }


    }








}
