class ZeroDUneFonction {

  public static void main (String [] args) {
    double a, b, m,e;
    e = 1E-5;
    a = 2.0;
    b = 4.0;
    m = (a + b) / 2;
    while (b - a > e & Math.abs(Math.sin(m)) > e) {
      if (Math.sin(a) * Math.sin(m) <= 0) {
        b = m;}   
      else { 
        a = m;}
      m = (a + b) / 2;}
    System.out.println(m);}}
