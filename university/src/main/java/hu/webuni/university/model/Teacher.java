package hu.webuni.university.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Audited
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
public class Teacher extends UniversityUser {

	@ManyToMany(mappedBy = "teachers")
	private Set<Course> courses;
	
	@Override
	public UserType getUserType() {
		return UserType.TEACHER;
	}
}
