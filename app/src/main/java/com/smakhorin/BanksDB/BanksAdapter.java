package com.smakhorin.BanksDB;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.BanksViewHolder> {
    public static final String TAG = BanksAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private Bank[] mBanks;


    public interface ListItemClickListener {
        void onEditClick(int position);

        void onDeleteClick(int position);
    }

    public BanksAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
    }

    public BanksAdapter(Bank[] banks, ListItemClickListener listener) {
        mOnClickListener = listener;
        mBanks = banks;
    }

    @Override
    public BanksViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.db_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        BanksViewHolder viewHolder = new BanksViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BanksAdapter.BanksViewHolder banksViewHolder, int i) {
        banksViewHolder.bind(mBanks[i]);
    }

    @Override
    public int getItemCount() {
        if(mBanks == null)
            return 0;
        return mBanks.length;
    }

    @Override
    public long getItemId(int position) {
        return mBanks[position].getId();
    }

    public Bank getElement(int position) {
        return mBanks[position];
    }

    public void setData(Bank[] banks) {
        mBanks = banks;
        notifyDataSetChanged();
    }

    /**
     * Cache of the children views for a list item.
     */
    class BanksViewHolder extends RecyclerView.ViewHolder {

        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView listItemNumberView;
        // Will display which ViewHolder is displaying this data
        TextView viewHolderIndex;

        TextView mName;
        TextView mAddress;
        TextView mPhone;
        TextView mEmployeeCount;
        TextView mWorkTime;
        TextView mEmail;

        ImageButton mEdit;
        ImageButton mDelete;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         * @param itemView The View that you inflated in
         *                 {@link BanksAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public BanksViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.tv_item_name);
            mAddress = itemView.findViewById(R.id.tv_item_address);
            mPhone = itemView.findViewById(R.id.tv_item_phone);
            mEmployeeCount = itemView.findViewById(R.id.tv_item_empoloyee_number);
            mWorkTime = itemView.findViewById(R.id.tv_item_work_hours);
            mEmail = itemView.findViewById(R.id.tv_item_email);

            mEdit = itemView.findViewById(R.id.ib_edit);
            mDelete = itemView.findViewById(R.id.ib_delete);

            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    mOnClickListener.onEditClick(clickedPosition);
                }
            });
            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    mOnClickListener.onDeleteClick(clickedPosition);
                }
            });
            //itemView.setOnClickListener(this);
        }

        /**
         * A method we wrote for convenience. This method will take an integer as input and
         * use that integer to display the appropriate text within a list item.
         * @param item Position of the item in the list
         */
        void bind(Bank item) {
            mName.setText(item.getName());
            mAddress.setText(item.getAddress());
            mPhone.setText(item.getPhone());
            mEmployeeCount.setText(String.valueOf(item.getEmployeeCount()));
            mWorkTime.setText(item.getWorkTime());
            mEmail.setText(item.getEmail());
        }

//        @Override
//        public void onClick(View v) {
//            int clickedPosition = getAdapterPosition();
//            mOnClickListener.onListItemClick(clickedPosition);
//        }
    }
}
