package hkucs.knowcourse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import hkucs.knowcourse.network.CourseEntry;
import hkucs.knowcourse.network.ImageRequester;

import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class CourseCardRecyclerViewAdapter extends RecyclerView.Adapter<CourseCardViewHolder> {

    private List<CourseEntry> productList;
    private ImageRequester imageRequester;

    CourseCardRecyclerViewAdapter(List<CourseEntry> productList) {
        this.productList = productList;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public CourseCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card, parent, false);
        return new CourseCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseCardViewHolder holder, int position) {
        if (productList != null && position < productList.size()) {
            CourseEntry product = productList.get(position);
            holder.productTitle.setText(product.title);
            holder.productPrice.setText(product.price);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
