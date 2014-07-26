package films;

public class Film {

	private int mPosition;
	private String mName;
	private String mDirector;
	private int mDate;
	private String mRating;
	private int mVotes;
	private int mProgress;
	private int mVote_difference;
	
	public Film(){}

	public int getVotes() {
		return mVotes;
	}

	public void setVotes(int pVotes) {
		this.mVotes = pVotes;
	}

	public String getRating() {
		return mRating;
	}

	public void setRating(String pRating) {
		this.mRating = pRating;
	}

	public String getDirector() {
		return mDirector;
	}

	public void setDirector(String pDirector) {
		this.mDirector = pDirector;
	}

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		this.mName = pName;
	}

	public int getPosition() {
		return mPosition;
	}

	public void setPosition(int pPosition) {
		this.mPosition = pPosition;
	}

	public int getDate() {
		return mDate;
	}

	public void setDate(int pDate) {
		this.mDate = pDate;
	}

	public int getProgress() {
		return mProgress;
	}

	public void setProgress(int mProgress) {
		this.mProgress = mProgress;
	}

	public int getVote_difference() {
		return mVote_difference;
	}

	public void setVote_difference(int mVote_difference) {
		this.mVote_difference = mVote_difference;
	}
	
	
}
