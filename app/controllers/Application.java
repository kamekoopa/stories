package controllers;

import models.utils.LoggedIn;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	public static Result index() {


//		Users u = new Users();
//		u.name = "user";
//		u.save();
//
//		Boxes b = new Boxes();
//		b.createdBy = u;
//		b.name = "testbox";
//		//b.save();
//
//		StoryCards s = new StoryCards();
//		//s.box = b;
//		s.createdBy = u;
//		s.front = "front";
//		s.back = "back";
//		s.points = 10;
//		s.done = false;
//		//s.save();
//
//		b.stories.add(s);
//		b.save();
//
//		System.out.println(u.id);
//		System.out.println(s.id);

		return ok(index.render("Your new application is ready."));
	}

	@LoggedIn
	public static Result logged(){

		return ok();
	}

}