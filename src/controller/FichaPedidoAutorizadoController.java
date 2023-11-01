package controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaPlayer;
import javax.swing.JOptionPane;
import model.Conexion;
import model.Sonido;

/**
 * @author Joaquín
 */
public class FichaPedidoAutorizadoController implements Initializable {
    
    String update_user = "",Pedido = "";

    @FXML
    private TextField txt_infoFecha;
    @FXML
    private TextField txt_infoProveedor;
    @FXML
    private TextArea txt_infoPedido;
    @FXML
    private Button btn_guardarPedido;
    @FXML
    private Button btn_eliminarPedido;
    @FXML
    private Button btn_pdfPedidos;
    
    MediaPlayer mediaplayer;
    Sonido noAutorizadoEliminarPedido = new Sonido();
    Sonido noAutorizadoModificarPedido = new Sonido();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        update_user = UsuariosRegistradosController.update_user;
       Pedido = MdPrincipalAutorizadoController.Pedido;
   
        //MOSTRAR LA INFORMACIÓN DEL PEDIDO------------
        
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select * from pedidos_provee where pedido = '" +Pedido + "'");
            ResultSet rs = pst.executeQuery();
            System.out.println("entra bien" + update_user);

            if (rs.next()) {
                //ID = rs.getInt("id_empleado");

                txt_infoFecha.setText(rs.getString("fecha"));
                txt_infoProveedor.setText(rs.getString("proveedor"));
                txt_infoPedido.setText(rs.getString("pedido"));
                    
            }

            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al cargar pedido." + e);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar!! Contacte con el administrador.");

        }
        
    }    

    @FXML
    private void GuardarPedido(ActionEvent event) {
        
        noAutorizadoModificarPedido.SonidonoAutorizadoModificarPedido();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informacion");
        alert.setContentText("No está autorizado para modificar el pedido.\nConsulte con el administrador.");
        alert.showAndWait();
        
    }

    @FXML
    private void EliminarPedido(ActionEvent event) {
        
        noAutorizadoEliminarPedido.SonidonoAutorizadoEliminarPedido();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informacion");
        alert.setContentText("No está autorizado para eliminar el pedido.\nConsulte con el administrador.");
        alert.showAndWait();
        
    }

    @FXML
    private void PdfPedidos(ActionEvent event) {
        
        Document documento = new Document();
        
        try{
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Pedidos_Proveedores.pdf"));
            
            documento.open();
           
            Font fuente= new Font();
            fuente.setSize(24);
            
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            //table.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell(" PEDIDOS A PROVEEDORES", PdfPCell.ALIGN_CENTER));
            //table.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
            documento.add(table);
                   
            //documento.add(new Paragraph("Informacion de los empleados.", fuente));
            documento.add(new Paragraph(" ", fuente));
            
            PdfPTable tabla = new PdfPTable(3);
    
            tabla.setWidthPercentage(100);
            
    Paragraph columna1 = new Paragraph("FECHA");
    columna1.getFont().setStyle(Font.BOLD);
    columna1.getFont().setSize(7);
    columna1.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna1);
    
    Paragraph columna2 = new Paragraph("PROVEEDOR");
    columna2.getFont().setStyle(Font.BOLD);
    columna2.getFont().setSize(7);
    columna2.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna2);
    
    Paragraph columna3 = new Paragraph("PEDIDO");
    columna3.getFont().setStyle(Font.BOLD);
    columna3.getFont().setSize(7);
    columna3.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna3);
    
    
            
           
            //tabla.addCell("Codigo");
            // tabla.addCell("Nombre");
            //tabla.addCell("Email");
            //tabla.addCell("Teléfono");
            //tabla.addCell("Centro");
            //tabla.addCell("Observaciones");
            //tabla.addCell("Moscosos");
            //tabla.addCell("Moscosos Cogidos");
            //tabla.addCell("Extras");
            //tabla.addCell("Sustituciones");
            //tabla.addCell("Vacaciones");
            //tabla.addCell("Vacaciones1");
            //tabla.addCell("Mes");
            //tabla.addCell("Mes1");
            
            try{
               Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement(
                        "select * from pedidos_provee");
                
                ResultSet rs = pst.executeQuery();
                
                if(rs.next()){
                    
                    do{
                        
                        Paragraph columna9 = new Paragraph(rs.getString(2));
                        columna9.getFont().setStyle(Font.ITALIC);
                        columna9.getFont().setSize(8);
                        tabla.addCell(columna9);
                               
                        /*Paragraph columna10 = new Paragraph(rs.getString(4));
                        columna10.getFont().setStyle(Font.BOLD);
                        columna10.getFont().setSize(8);
                        tabla.addCell(columna10);*/
                        
                        Paragraph columna11 = new Paragraph(rs.getString(3));
                        columna11.getFont().setStyle(Font.ITALIC);
                        columna11.getFont().setSize(8);
                        tabla.addCell(columna11);
                        
                        Paragraph columna12 = new Paragraph(rs.getString(4));
                        columna12.getFont().setStyle(Font.ITALIC);
                        columna12.getFont().setSize(8);
                        tabla.addCell(columna12);
                        
                       //tabla.addCell(rs.getString(1));
                       //tabla.addCell(rs.getString(2));
                       //tabla.addCell(rs.getString(3));
                       //tabla.addCell(rs.getString(4));
                       //tabla.addCell(rs.getString(5));
                       //tabla.addCell(rs.getString(6));
                       //tabla.addCell(rs.getString(7));
                       //tabla.addCell(rs.getString(8));
                       //tabla.addCell(rs.getString(9));
                       //tabla.addCell(rs.getString(10));
                       //tabla.addCell(rs.getString(11));
                       //tabla.addCell(rs.getString(12));
                       //tabla.addCell(rs.getString(13));
                       //tabla.addCell(rs.getString(14));
                    }while(rs.next());
                    documento.add(tabla);
                }
                
            }catch(SQLException | DocumentException e){
                
            }
            documento.close();
            
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(null);
            alert1.setTitle("Informacion");
            alert1.setContentText("El informe se creó con éxito.");
            alert1.showAndWait();
          
        }catch(FileNotFoundException | DocumentException | HeadlessException e){
            
        }
        
    }
    
    public PdfPCell getCell(String text, int alignment) {
    PdfPCell cell = new PdfPCell(new Phrase(text));
    cell.setPadding(0);
    cell.setHorizontalAlignment(alignment);
    cell.setBorder(PdfPCell.NO_BORDER);
    return cell;
}
    
}
