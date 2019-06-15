package com.liyu.fakeweather.hp;

import java.util.List;

public class SimpleDatasBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * rainfall : 0.546885

         * stressy : null
         * time : 20190610152642
         * t1 : 28.693075

         * humidity1 : 0.348525

         * pressure1 : 119.867779

         * a1 : 0.379684and0.371396and0.365439and0.379710and0.371249and0.365420and0.377114and0.368774and0.362929and0.372410and0.364195and0.358574and0.367962and0.359765and0.354289and0.362745and0.355045and0.349278and0.359802and0.351887

         */

        private String rainfall;
        private String stressy;
        private String time;
        private String t1;
        private String humidity1;
        private String pressure1;
        private String a1;

        public String getRainfall() {
            return rainfall;
        }

        public void setRainfall(String rainfall) {
            this.rainfall = rainfall;
        }

        public String getStressy() {
            return stressy;
        }

        public void setStressy(String stressy) {
            this.stressy = stressy;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getT1() {
            return t1;
        }

        public void setT1(String t1) {
            this.t1 = t1;
        }

        public String getHumidity1() {
            return humidity1;
        }

        public void setHumidity1(String humidity1) {
            this.humidity1 = humidity1;
        }

        public String getPressure1() {
            return pressure1;
        }

        public void setPressure1(String pressure1) {
            this.pressure1 = pressure1;
        }

        public String getA1() {
            return a1;
        }

        public void setA1(String a1) {
            this.a1 = a1;
        }
    }
}
