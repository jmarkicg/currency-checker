package hr.markic.currency.checker.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "currency")
public class Currency {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String code;
}

