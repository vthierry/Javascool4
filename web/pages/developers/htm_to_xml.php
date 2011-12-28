<?php # Use: imp ; ereg_replace

/** Converts a HTML string into a XHTML logical-structure.
 * - Accentuation is re-encoded in ISO-8859-1, spurious constructs (comments, instructions, ..) are deleted and empty tags are terminated by '/>'.
 * - This is a fragible command in the sense that unexpected spurious or non-well formed HTML syntax may defeat it.
 * @param string The HTML string.
 * @return The XHTML transformation
 */
function htm_to_xml($string) {
  // Eliminate html accentuation
  $string = 
    ereg_replace("&agrave;", "�", 
    ereg_replace("&acirc;", "�", 
    ereg_replace("&eacute;", "�", 
    ereg_replace("&egrave;", "�", 
    ereg_replace("&euml;", "�", 
    ereg_replace("&ecirc;", "�", 
    ereg_replace("&iuml;", "�", 
    ereg_replace("&icirc;", "�", 
    ereg_replace("&ouml;", "�", 
    ereg_replace("&ocirc;", "�", 
    ereg_replace("&ugrave;", "�", 
    ereg_replace("&ccedil;", "�", 
  $string))))))))))));
  // Eliminate spurious constructs
  $string = ereg_replace("<[!?][^>]*>","", ereg_replace("&nbsp;","", $string));
  // Encapsulate non XML HTML constructs
  $string = ereg_replace("(<(meta|img|hr|br|link)[^>/]*)/?>","\\1/>", $string);
  return $string;
}
?>
