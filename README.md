# slidetimeaxis
1.添加以下依赖
  com.lyf.slidetimeaxis:slidetimeaxis:0.0.1
2.在xml使用
     <com.lyf.slidetimeaxis.XLHStepView
        android:id="@+id/zidingyi"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="10dp"
        android:layout_marginRight="10dp"
        android:layout_marginTop="20dp"
        xlh:barColor="#51C6EF"
        xlh:canDrag="true"
        xlh:currentStep="3"
        xlh:progressColor="#085EB3"
        xlh:stepCount="3"
        xlh:stepNormalColor="#76BD30"
        xlh:stepSelectedColor="#F66936" >
    </com.lyf.slidetimeaxis.XLHStepView>
 3.在activity中
      XLHStepView view=(XLHStepView) findViewById(R.id.zidingyi);
        //添加当前的回调
        view.setStepChangedListener(new XLHStepView.OnStepChangedListener() {

            @Override
            public void onStepChanged(int currentStep) {
                Log.i("i","change:"+currentStep);
            }
        });
  
