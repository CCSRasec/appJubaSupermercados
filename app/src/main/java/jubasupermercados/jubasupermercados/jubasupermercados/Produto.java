package jubasupermercados.jubasupermercados.jubasupermercados;

public class Produto {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_prod_ofer() {
        return img_prod_ofer;
    }

    public void setImg_prod_ofer(String img_prod_ofer) {
        this.img_prod_ofer = img_prod_ofer;
    }

    public String getNome_prod() {
        return nome_prod;
    }

    public void setNome_prod(String nome_prod) {
        this.nome_prod = nome_prod;
    }

    public String getPrecoDe() {
        return precoDe;
    }

    public void setPrecoDe(String precoDe) {
        this.precoDe = precoDe;
    }

    public String getPrecoPor() {
        return precoPor;
    }

    public void setPrecoPor(String precoPor) {
        this.precoPor = precoPor;
    }
    public String getEconomiaDe() {
        return economiaDe;
    }

    public void setEconomiaDe(String economiaDe) {
        this.economiaDe = economiaDe;
    }

    public String getProdCategoria() {
        return prodCategoria;
    }

    public void setProdCategoria(String prodCategoria) {
        this.prodCategoria = prodCategoria;
    }
    public int getCodProdCategoria() {
        return codProdCategoria;
    }

    public void setCodProdCategoria(int codProdCategoria) {
        this.codProdCategoria = codProdCategoria;
    }


    String id;
    String img_prod_ofer;
    String nome_prod;
    String precoDe;
    String precoPor;
    String economiaDe;
    String prodCategoria;
    String inicio, fim;

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getSeqProduto() {
        return seqProduto;
    }

    public void setSeqProduto(String seqProduto) {
        this.seqProduto = seqProduto;
    }

    String seqProduto;
    int codProdCategoria;

}
