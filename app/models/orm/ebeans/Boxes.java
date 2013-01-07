package models.orm.ebeans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
public class Boxes extends Model {
	private static final long serialVersionUID = 6319698219575254275L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	@Constraints.Required

	@ManyToOne
	public Users createdBy;

	@OneToMany(cascade = CascadeType.ALL)
	public List<StoryCards> stories = new ArrayList<>();

	@Constraints.Required
	public String name;

	@CreatedTimestamp
	public Timestamp createdAt;

	@Version
	public Timestamp updatedAt;

	public static Finder<Long, Boxes> find = new Finder<>(Long.class, Boxes.class);
}
