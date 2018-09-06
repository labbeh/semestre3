public abstract class DeuxRoues {
	protected int prix; /* prix total, taxe incluse */

	public DeuxRoues(int lePrix) {
		prix = lePrix;
	}

	public abstract int taxe();

	public String toString() {
		return "coute " + prix + " dont " + taxe() + " en taxe";
	}
}
