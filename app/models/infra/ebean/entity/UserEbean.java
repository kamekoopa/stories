package models.infra.ebean.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name = "users")
public class UserEbean extends Model {
	private static final long serialVersionUID = -3214561612340592677L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;

	@Constraints.Required
	public String name;

	@Constraints.Required
	public String pass;

	@OneToMany(targetEntity = BoxEbean.class, mappedBy = "createdBy", cascade=CascadeType.ALL)
	public List<BoxEbean> createdBoxes = new ArrayList<>();

	@OneToMany
	public List<StoryCardEbean> createdCards = new ArrayList<>();

	@CreatedTimestamp
	public Timestamp createdAt;

	@Version
	public Timestamp updatedAt;

	public static final Finder<Long, UserEbean> find = new Finder<Long, UserEbean>(Long.class, UserEbean.class);
}
