package jp.rainbowdevil.snippets.ui.windows.syntax;

import java.util.Collection;

public class SyntaxData {
	private String extension;

	  private Collection<String> keywords;

	  private String punctuation;

	  private String comment;

	  private String multiLineCommentStart;

	  private String multiLineCommentEnd;

	  /**
	   * Constructs a SyntaxData
	   * 
	   * @param extension
	   *          the extension
	   */
	  public SyntaxData(String extension) {
	    this.extension = extension;
	  }

	  /**
	   * Gets the extension
	   * 
	   * @return String
	   */
	  public String getExtension() {
	    return extension;
	  }

	  /**
	   * Gets the comment
	   * 
	   * @return String
	   */
	  public String getComment() {
	    return comment;
	  }

	  /**
	   * Sets the comment
	   * 
	   * @param comment
	   *          The comment to set.
	   */
	  public void setComment(String comment) {
	    this.comment = comment;
	  }

	  /**
	   * Gets the keywords
	   * 
	   * @return Collection
	   */
	  public Collection<String> getKeywords() {
	    return keywords;
	  }

	  /**
	   * Sets the keywords
	   * 
	   * @param keywords
	   *          The keywords to set.
	   */
	  public void setKeywords(Collection<String> keywords) {
	    this.keywords = keywords;
	  }

	  /**
	   * Gets the multiline comment end
	   * 
	   * @return String
	   */
	  public String getMultiLineCommentEnd() {
	    return multiLineCommentEnd;
	  }

	  /**
	   * Sets the multiline comment end
	   * 
	   * @param multiLineCommentEnd
	   *          The multiLineCommentEnd to set.
	   */
	  public void setMultiLineCommentEnd(String multiLineCommentEnd) {
	    this.multiLineCommentEnd = multiLineCommentEnd;
	  }

	  /**
	   * Gets the multiline comment start
	   * 
	   * @return String
	   */
	  public String getMultiLineCommentStart() {
	    return multiLineCommentStart;
	  }

	  /**
	   * Sets the multiline comment start
	   * 
	   * @param multiLineCommentStart
	   *          The multiLineCommentStart to set.
	   */
	  public void setMultiLineCommentStart(String multiLineCommentStart) {
	    this.multiLineCommentStart = multiLineCommentStart;
	  }

	  /**
	   * Gets the punctuation
	   * 
	   * @return String
	   */
	  public String getPunctuation() {
	    return punctuation;
	  }

	  /**
	   * Sets the punctuation
	   * 
	   * @param punctuation
	   *          The punctuation to set.
	   */
	  public void setPunctuation(String punctuation) {
	    this.punctuation = punctuation;
	  }
}
