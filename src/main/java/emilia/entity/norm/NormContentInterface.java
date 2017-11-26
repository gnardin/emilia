package emilia.entity.norm;

public interface NormContentInterface {
  
  /**
   * Check whether the content parameter matches the norm content
   * 
   * @param value
   *          Content to be checked whether it matches to the norm content
   * @return True if it matches, False otherwise
   */
  public boolean match( Object value );
  
  
  /**
   * Check whether the content parameter complies or violates the prescribed
   * norm in the norm content
   * 
   * @param value
   *          Content to be checked whether it complies or violates the norm
   *          prescription in the norm content
   * @return True if it complies, False otherwise
   */
  public boolean comply( Object value );
}