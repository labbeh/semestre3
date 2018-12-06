package tp3;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

import simbad.sim.Agent;

public class RobotEnnemi extends Agent
{

	public RobotEnnemi(Vector3d pos, String name)
	{
		super(pos, name);
		
	}
	
	/**
	 * On redéfini cette méthode pour pourvoir changer la couleur de ces robots
	 * */
	@Override
	protected void create3D(){
		  
        Color3f color2 = new Color3f(0.0f,0.8f,0.0f);
        Color3f color = new Color3f(1.0f,0.0f,0.0f);
        // body
        Appearance  appear = new Appearance();
        
        color.clampMax(0.8f);
        material.setDiffuseColor(color);
        
        material.setSpecularColor(black);
        appear.setMaterial(material);
        int flags = Primitive.GEOMETRY_NOT_SHARED | Primitive.ENABLE_GEOMETRY_PICKING | Primitive.GENERATE_NORMALS;
        flags |= Primitive.ENABLE_APPEARANCE_MODIFY;
      
        body = new Cylinder(radius,height,flags,appear);
        
       /* // allow geom intersect on each geom of the primitive cylinder
        allowIntersect(body.getShape(Cylinder.BODY));
        allowIntersect(body.getShape(Cylinder.TOP));*/
        // we must be able to change the pick flag of the agent 
        body.setCapability(Node.ALLOW_PICKABLE_READ);
        body.setCapability(Node.ALLOW_PICKABLE_WRITE);
        body.setPickable(true);
        body.setCollidable(true);
        addChild(body);
        
        // direction indicator
        float coords[]={
                radius/2,height,-radius/2,//
                radius/2,height,radius/2,//
                radius,height,0 //
        };
        float normals[]={
                0,1,0,
                0,1,0,
                0,1,0
        };
        TriangleArray tris = new TriangleArray( coords.length,
    	        GeometryArray.COORDINATES|GeometryArray.NORMALS);
        
        tris.setCoordinates( 0, coords );
        tris.setNormals( 0, normals );
	
	    appear = new Appearance();
	    appear.setMaterial(new Material(color2, black,
	                                  color2, white, 100.0f));

	    Shape3D s = new Shape3D(tris,appear);
	    s.setPickable(false);
	    addChild(s);
	    
	    // Add bounds for interactions and collision
	    Bounds bounds = new BoundingSphere(new Point3d(0,0,0),radius);
	    setBounds(bounds);
   
    }

}
