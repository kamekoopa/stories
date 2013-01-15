package models.infra.ebean.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name="story_cards")
public class StoryCardEbean extends Model {
	private static final long serialVersionUID = -7623805467922298373L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;

	@Constraints.Required
	@ManyToOne(targetEntity = BoxEbean.class)
	@JoinColumn(name="box_id", referencedColumnName = "id")
	public BoxEbean box;

	@Constraints.Required
	@ManyToOne
	public UserEbean createdBy;

	@Constraints.Required
	public String front;

	@Constraints.Required
	public String back;

	@Constraints.Required
	public Integer points;

	@Constraints.Required
	public Boolean done;

	@CreatedTimestamp
	public Timestamp createdAt;

	@Version
	public Timestamp updatedAt;

	public static Finder<Long, StoryCardEbean> find = new Finder<>(Long.class, StoryCardEbean.class);
}
