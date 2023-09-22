package com.jtk.ps.api.dto.cv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtk.ps.api.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CVGetResponse {
    private Integer id;

    private String name;

    private String nickname;

    private String address;

    @JsonProperty("no_phone")
    private String noPhone;

    private String email;

    private String religion;

    private Character gender;

    private String place;

    private String birthday;

    private Boolean marriage;

    private String citizenship;

    @JsonProperty("domicile_id")
    private Integer domicileId;

    private List<Education> educations;

    private List<Experience> experiences;

    private List<Organization> organizations;

    private List<CVCompetenceAndType> competencies;

    @JsonProperty("jobscopes")
    private List<CVJobScope> jobScopes;

    private List<Skill> skills;

    private List<Seminar> seminars;

    private List<Championship> championships;
}
