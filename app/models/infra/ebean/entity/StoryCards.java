package models.infra.ebean.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
public class StoryCards extends Model {
	private static final long serialVersionUID = -7623805467922298373L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;

	@Constraints.Required
	@ManyToOne(cascade = CascadeType.ALL)
	public Boxes box;

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

	public static Finder<Long, StoryCards> find = new Finder<>(Long.class, StoryCards.class);
}
