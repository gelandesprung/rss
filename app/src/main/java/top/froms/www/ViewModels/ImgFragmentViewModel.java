package top.froms.www.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.DataSource.Factory;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;
import java.util.ArrayList;
import java.util.List;
import top.froms.www.repo.IMGRepo;
import top.froms.www.repo.ImgData;

public class ImgFragmentViewModel extends BaseViewModel {

    private static final int NUM_PER_PAGE = 4;
    private List<ImgData> dataInDB = new ArrayList<>();
    private LiveData<PagedList<ImgData>> dataMutableLiveData;
    private IMGRepo repo = new IMGRepo();

    public LiveData<PagedList<ImgData>> loadImages() {
        if (dataMutableLiveData == null) {
            dataMutableLiveData = new LivePagedListBuilder(new Factory() {
                @NonNull
                @Override
                public DataSource create() {
                    return positionalDataSource;
                }
            },
                    new PagedList.Config.Builder().setPageSize(NUM_PER_PAGE)
                            .setPrefetchDistance(1)
                            .setMaxSize(30)
                            .setEnablePlaceholders(false).setInitialLoadSizeHint(NUM_PER_PAGE)
                            .build()).build();
        }
        return dataMutableLiveData;
    }


    private final PageKeyedDataSource<Integer, List<ImgData>> pageKeyedDataSource = new PageKeyedDataSource<Integer, List<ImgData>>() {
        @Override
        public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                @NonNull LoadInitialCallback<Integer, List<ImgData>> callback) {
        }

        @Override
        public void loadBefore(@NonNull LoadParams<Integer> params,
                @NonNull LoadCallback<Integer, List<ImgData>> callback) {

        }

        @Override
        public void loadAfter(@NonNull LoadParams<Integer> params,
                @NonNull LoadCallback<Integer, List<ImgData>> callback) {

        }
    };
    private final PositionalDataSource<ImgData> positionalDataSource = new PositionalDataSource<ImgData>() {
        List<ImgData> dataList = new ArrayList<>();

        @Override
        public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<ImgData> callback) {
            dataList.addAll(repo.data());
            List<ImgData> tmp = new ArrayList<>(dataList.subList(params.requestedStartPosition, params.pageSize));
            callback.onResult(tmp, params.requestedStartPosition);
        }

        @Override
        public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<ImgData> callback) {
            if (params.startPosition >= dataList.size()) {
                dataList.addAll(repo.data());
            }
            List<ImgData> tmp = new ArrayList<>(dataList);
            if (params.startPosition + params.loadSize > dataList.size()) {
                callback.onResult(tmp.subList(params.startPosition, tmp.size()));
            } else {
                callback.onResult(tmp.subList(params.startPosition, params.startPosition + params.loadSize));
            }
        }
    };

    @Override
    protected void onCleared() {
        dataInDB=null;
        super.onCleared();
    }
}
