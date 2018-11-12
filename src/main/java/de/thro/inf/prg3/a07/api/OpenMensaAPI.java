package de.thro.inf.prg3.a07.api;

import de.thro.inf.prg3.a07.model.Meal;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by Peter Kurfer on 11/19/17.
 */

public interface OpenMensaAPI {

	/**
	 * Get meals of a specific date
	 * @param date date for which the meals should be retrieved
	 * @remark results in a call like this GET /canteens/229/days/2017-11-22/meals
	 * @return Retrofit Call wrapper object
	 */
	@GET("canteens/229/days/{date}/meals")
	Call<List<Meal>> getMeals(@Path("date") String date);
}
