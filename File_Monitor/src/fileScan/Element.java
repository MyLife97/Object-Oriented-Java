package fileScan;

public class Element {
	public static final String rename = "renamed";
	public static final String modified = "modified";
	public static final String pathchange = "pathchanged";
	public static final String sizechange = "sizechanged";
	public static final String summary = "summary";
	public static final String detail = "detail";
	public static final String recover = "recover";
	
	public static final String directory = "directory";
	public static final String file = "file";
	
	public static final long not_exist_size = -1;
	public static final boolean trigger_on = true;
	public static final boolean trigger_off = false;
	public static final int max_number = 120;
}
