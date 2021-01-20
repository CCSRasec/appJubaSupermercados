package jubasupermercados.jubasupermercados.jubasupermercados;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;
public class MainSliderAdapter extends SliderAdapter {

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        switch (position) {
           /* case 0:
                viewHolder.bindImageSlide("http://ofertafacil.net/03550647001238.jpg");
                break;*/
            case 0:
                viewHolder.bindImageSlide("http://www.smguanabara.com.br/upload/banner/121.jpg");
                break;
            case 1:
                viewHolder.bindImageSlide("http://www.smguanabara.com.br/upload/banner/127.png");
                break;
            case 2:
                viewHolder.bindImageSlide("http://www.smguanabara.com.br/upload/banner/126.png");
                break;
        }
    }

}
