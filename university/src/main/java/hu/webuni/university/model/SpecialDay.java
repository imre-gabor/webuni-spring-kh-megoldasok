package hu.webuni.university.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Audited
public class SpecialDay {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include
	private int id;
	private LocalDate sourceDay;
	private LocalDate targetDay;	//null means sourceDay is holiday
}
