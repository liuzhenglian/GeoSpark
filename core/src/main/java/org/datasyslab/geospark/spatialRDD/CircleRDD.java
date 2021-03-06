/**
 * FILE: CircleRDD.java
 * PATH: org.datasyslab.geospark.spatialRDD.CircleRDD.java
 * Copyright (c) 2015-2017 GeoSpark Development Team
 * All rights reserved.
 */
package org.datasyslab.geospark.spatialRDD;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.datasyslab.geospark.geometryObjects.Circle;


import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

// TODO: Auto-generated Javadoc
/**
 * The Class CircleRDD.
 */
public class CircleRDD extends SpatialRDD<Circle> {


	/**
	 * Instantiates a new circle RDD.
	 *
	 * @param circleRDD the circle RDD
	 */
	public CircleRDD(JavaRDD<Circle> circleRDD) {
		this.rawSpatialRDD = circleRDD;
	}

	/**
	 * Instantiates a new circle RDD.
	 *
	 * @param circleRDD the circle RDD
	 * @param sourceEpsgCRSCode the source epsg CRS code
	 * @param targetEpsgCRSCode the target epsg CRS code
	 */
	public CircleRDD(JavaRDD<Circle> circleRDD, String sourceEpsgCRSCode, String targetEpsgCRSCode) {
		this.rawSpatialRDD = circleRDD;
		this.CRSTransform(sourceEpsgCRSCode, targetEpsgCRSCode);
	}
	
	/**
	 * Instantiates a new circle RDD.
	 *
	 * @param spatialRDD the spatial RDD
	 * @param Radius the radius
	 */
	public CircleRDD(SpatialRDD spatialRDD, Double Radius) {
		final Double radius = Radius;
		this.rawSpatialRDD = spatialRDD.rawSpatialRDD.map(new Function<Object, Object>() {

			public Object call(Object v1) {

				return new Circle((Geometry) v1, radius);
			}
		});
		this.CRStransformation = spatialRDD.CRStransformation;
		this.sourceEpsgCode = spatialRDD.sourceEpsgCode;
		this.targetEpgsgCode = spatialRDD.targetEpgsgCode;
	}


	/**
	 * Gets the center point as spatial RDD.
	 *
	 * @return the center point as spatial RDD
	 */
	public PointRDD getCenterPointAsSpatialRDD() {
		return new PointRDD(this.rawSpatialRDD.map(new Function<Circle, Point>() {

			public Point call(Circle circle) {
				return (Point)circle.getCenterGeometry();
			}
		}));

	}
	
	/**
	 * Gets the center polygon as spatial RDD.
	 *
	 * @return the center polygon as spatial RDD
	 */
	public PolygonRDD getCenterPolygonAsSpatialRDD() {
		return new PolygonRDD(this.rawSpatialRDD.map(new Function<Circle, Polygon>() {

			public Polygon call(Circle circle) {

				return (Polygon)circle.getCenterGeometry();
			}
		}));
	}
	
	/**
	 * Gets the center line string RDD as spatial RDD.
	 *
	 * @return the center line string RDD as spatial RDD
	 */
	public LineStringRDD getCenterLineStringRDDAsSpatialRDD() {
		return new LineStringRDD(this.rawSpatialRDD.map(new Function<Circle, LineString>() {

			public LineString call(Circle circle) {

				return (LineString) circle.getCenterGeometry();
			}
		}));
	}
	
	/**
	 * Gets the center rectangle RDD as spatial RDD.
	 *
	 * @return the center rectangle RDD as spatial RDD
	 */
	public RectangleRDD getCenterRectangleRDDAsSpatialRDD() {
		return new RectangleRDD(this.rawSpatialRDD.map(new Function<Circle, Polygon>() {

			public Polygon call(Circle circle) {

				return (Polygon) circle.getCenterGeometry();
			}
		}));
	}
}
