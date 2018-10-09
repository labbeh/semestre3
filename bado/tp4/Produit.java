public class Produit{
	private int np;
	private String lib;
	private String coul;
	private int qs;

	public int getNp(){return np;}
	public String getLib(){return lib;}
	public String getCoul(){return coul;}
	public int getQs(){return qs;}

	public void setNp(int np){this.np=np;}
	public void setLib(String lib){this.lib=lib;}
	public void setCoul(String coul){this.coul=coul;}
	public void setQs(int qs){this.qs=qs;}

	public Produit(){}
	public Produit(int np , String lib, String coul, int qs){
		setNp(np);setLib(lib);setCoul(coul);setQs(qs);
	}

	public String toString(){
		return np+"," + lib +"," + coul +"," +qs;
	}
}


