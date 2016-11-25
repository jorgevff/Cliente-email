public class MailClient
{
    // The server used for sending and receiving.
    private MailServer server;
    // The user running this client.
    private String user;
    //Si es true, esta activida la respuesta automatica
    private boolean respuestaAuto;
    
    private String asuntoRespuestaAuto;

    private String mensajeRespuestaAuto;
    
    private MailItem guardarMensaje;

    /**
     * Create a mail client run by user and attached to the given server.
     */
    public MailClient(MailServer server, String user)
    {
        this.server = server;
        this.user = user;
        respuestaAuto = false;
        asuntoRespuestaAuto = "";
        mensajeRespuestaAuto = "";
        guardarMensaje = null;
        
    }

    /**
     * Return the next mail item (if any) for this user.
     */
    public MailItem getNextMailItem()
    {
        // Recibimos algo del servidor
        MailItem item = server.getNextMailItem(user);
        // Si lo que recibimos es un email y la respuesta automatica esta activada...
        if(respuestaAuto && item != null){
          // Enviamos un correo de respuesta automaticamente
          // Creamos el email
          //MailItem email = new MailItem(user, item.getFrom(), 
           //                             asuntoRespuestaAuto, mensajeRespuestaAuto);
          // Enviamos el email        
          //server.post(email);
          sendMailItem(item.getFrom(), asuntoRespuestaAuto, mensajeRespuestaAuto);
        }
        if (item != null){
            guardarMensaje = item;
        }
        // Devolvemos lo recibido por el servidor
        return item;
    }

    /**
     * Print the next mail item (if any) for this user to the text 
     * terminal.
     */
    public void printNextMailItem()
    {
        /**MailItem item = getNextMailItem();
         * asi seria mas correcto, estoy invocando el metodo mas arriba
         * asique no se pone nada delante con el punto, como server. y no se 
         * añade valores(user) ya que arriba esta creado ya con parametros
         */
        MailItem item = getNextMailItem();
        
        if(respuestaAuto && item != null){
            //con senMailItem creamos un email y lo enviamos, quedando alojado en el servidor
            //sendMailItem(item.getFrom(), asuntoRespuestaAuto, mensajeRespuestaAuto);
            System.out.println(asuntoRespuestaAuto);
            System.out.println(mensajeRespuestaAuto);
          }
        else if(item == null) {
            System.out.println("No new mail.");
        }
        else {
            item.print();
        }
    }

    /**
     * Send the given message to the given recipient via
     * the attached mail server.
     * @param to The intended recipient.
     * @param message The text of the message to be sent.
     */
    public void sendMailItem(String to, String subject, String message)
    {
        MailItem item = new MailItem(user, to, message, subject);
        server.post(item);
    }
    
    /**
     * Imprime por pantalla el numero de emails que hay
     * en el servidor para nosotros sin descargarlos
     */
    public void getNumMensajesNoLeidos()
    {
         System.out.println("El usuario "+ user + " tiene " + 
                            server.howManyMailItems(user) + " mensajes."); 
    }
    
    
    
    /**
     * Permite configurar el texto del asunto y del mensaje de la respuesta
     * automica.
     */
    public void configurarRespuestaAutomatica(String asuntoRespuestaAuto,String mensajeRespuestaAuto)
    {
        //con this se puede sobrecargar los atributos
        this.asuntoRespuestaAuto = asuntoRespuestaAuto;
        this.mensajeRespuestaAuto = mensajeRespuestaAuto;
    
    }
    
    /**
     * Habilita o deshabilita la respuesta automática
     */   
   // public void habilitaRespuestaAuto(boolean activar)
   // {
   //   respuestaAuto = activar;
    //}
    
    public void habilitaRespuesta()
    {
       if(respuestaAuto == false){
          
           respuestaAuto = true;
        }
        else
        {
            respuestaAuto = false;
        }
      /** tambien valdria con una sola linea de esta forma
       * respuestaAuto = !respuestaAuto;
       * siempre se ejecuta las sentencia del if cuando es verdadeero
       * osea va a entrar siempre que la condicion sera verdadera
       * ejempolo if(respuestaAuto) o if(respuestaAuto == false)
       */
    }
    
    public void mostrarEmailGuardado()
    {
       guardarMensaje.print();
    }
    
    
}







