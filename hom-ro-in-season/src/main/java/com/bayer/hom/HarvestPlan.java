package com.bayer.hom;

import java.util.Objects;

public class HarvestPlan {
	private String harvest_plan_date;
	private String user;
	private String harv_date;
	private String region;
	private String grower;
	private String field;
	private String hybrid;
	private int trucks;
	private int trucks_assigned;
	private double area;
	private double ton_rw;
	private String harvest_time;
	private String comments;
	private String timestamp;

	public HarvestPlan(String harvest_plan_date, String user, String harv_date, String region, String grower,
			String field, String hybrid, int trucks, int trucks_assigned, double area, double ton_rw,
			String harvest_time, String comments, String timestamp) {
		super();
		this.harvest_plan_date = harvest_plan_date;
		this.user = user;
		this.harv_date = harv_date;
		this.region = region;
		this.grower = grower;
		this.field = field;
		this.hybrid = hybrid;
		this.trucks = trucks;
		this.trucks_assigned = trucks_assigned;
		this.area = area;
		this.ton_rw = ton_rw;
		this.harvest_time = harvest_time;
		this.comments = comments;
		this.timestamp = timestamp;
	}

	/**
	 * @return the harvest_plan_date
	 */
	public String getHarvest_plan_date() {
		return harvest_plan_date;
	}

	/**
	 * @param harvest_plan_date the harvest_plan_date to set
	 */
	public void setHarvest_plan_date(String harvest_plan_date) {
		this.harvest_plan_date = harvest_plan_date;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the harv_date
	 */
	public String getHarv_date() {
		return harv_date;
	}

	/**
	 * @param harv_date the harv_date to set
	 */
	public void setHarv_date(String harv_date) {
		this.harv_date = harv_date;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the grower
	 */
	public String getGrower() {
		return grower;
	}

	/**
	 * @param grower the grower to set
	 */
	public void setGrower(String grower) {
		this.grower = grower;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the hybrid
	 */
	public String getHybrid() {
		return hybrid;
	}

	/**
	 * @param hybrid the hybrid to set
	 */
	public void setHybrid(String hybrid) {
		this.hybrid = hybrid;
	}

	/**
	 * @return the trucks
	 */
	public int getTrucks() {
		return trucks;
	}

	/**
	 * @param trucks the trucks to set
	 */
	public void setTrucks(int trucks) {
		this.trucks = trucks;
	}

	/**
	 * @return the trucks_assigned
	 */
	public int getTrucks_assigned() {
		return trucks_assigned;
	}

	/**
	 * @param trucks_assigned the trucks_assigned to set
	 */
	public void setTrucks_assigned(int trucks_assigned) {
		this.trucks_assigned = trucks_assigned;
	}

	/**
	 * @return the area
	 */
	public double getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(double area) {
		this.area = area;
	}

	/**
	 * @return the ton_rw
	 */
	public double getTon_rw() {
		return ton_rw;
	}

	/**
	 * @param ton_rw the ton_rw to set
	 */
	public void setTon_rw(double ton_rw) {
		this.ton_rw = ton_rw;
	}

	/**
	 * @return the harvest_time
	 */
	public String getHarvest_time() {
		return harvest_time;
	}

	/**
	 * @param harvest_time the harvest_time to set
	 */
	public void setHarvest_time(String harvest_time) {
		this.harvest_time = harvest_time;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(area, comments, field, grower, harv_date, harvest_plan_date, harvest_time, hybrid, region,
				timestamp, ton_rw, trucks, trucks_assigned, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HarvestPlan other = (HarvestPlan) obj;
		return Double.doubleToLongBits(area) == Double.doubleToLongBits(other.area)
				&& Objects.equals(comments, other.comments) && Objects.equals(field, other.field)
				&& Objects.equals(grower, other.grower) && Objects.equals(harv_date, other.harv_date)
				&& Objects.equals(harvest_plan_date, other.harvest_plan_date)
				&& Objects.equals(harvest_time, other.harvest_time) && Objects.equals(hybrid, other.hybrid)
				&& Objects.equals(region, other.region) && Objects.equals(timestamp, other.timestamp)
				&& Double.doubleToLongBits(ton_rw) == Double.doubleToLongBits(other.ton_rw) && trucks == other.trucks
				&& trucks_assigned == other.trucks_assigned && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "HarvestPlan [harvest_plan_date=" + harvest_plan_date + ", user=" + user + ", harv_date=" + harv_date
				+ ", region=" + region + ", grower=" + grower + ", field=" + field + ", hybrid=" + hybrid + ", trucks="
				+ trucks + ", trucks_assigned=" + trucks_assigned + ", area=" + area + ", ton_rw=" + ton_rw
				+ ", harvest_time=" + harvest_time + ", comments=" + comments + ", timestamp=" + timestamp + "]";
	}

}
