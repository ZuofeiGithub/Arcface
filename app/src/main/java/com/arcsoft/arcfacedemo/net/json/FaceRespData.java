package com.arcsoft.arcfacedemo.net.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaceRespData {

    /**
     * error_code : 0
     * error_msg : SUCCESS
     * log_id : 1345050711592261112
     * timestamp : 1551159226
     * cached : 0
     * result : {"thresholds":{"frr_1e-4":0.05,"frr_1e-3":0.3,"frr_1e-2":0.9},"face_liveness":1,"face_list":[{"face_token":"54a0acd9361d3178930d8d05fcb65e53","location":{"left":105.58,"top":141.85,"width":540,"height":586,"rotation":0},"face_probability":1,"angle":{"yaw":2.55,"pitch":-4.55,"roll":-3.8},"liveness":{"livemapscore":1},"age":35,"beauty":43.42,"expression":{"type":"none","probability":1},"face_shape":{"type":"square","probability":0.51},"gender":{"type":"male","probability":1},"glasses":{"type":"common","probability":1},"race":{"type":"yellow","probability":1},"landmark":[{"x":259.16,"y":268.23},{"x":497.86,"y":266.87},{"x":380.01,"y":376.35},{"x":380.43,"y":538.05}],"landmark72":[{"x":106.12,"y":323.4},{"x":106.93,"y":411.38},{"x":115.81,"y":498.75},{"x":140.04,"y":585.8},{"x":206.79,"y":669.9},{"x":295.47,"y":714.04},{"x":383.81,"y":726.49},{"x":470.8,"y":712.03},{"x":558.97,"y":664.13},{"x":620.71,"y":579.08},{"x":641.45,"y":490.68},{"x":647.43,"y":402.17},{"x":646.08,"y":315.71},{"x":205.34,"y":277.22},{"x":230.2,"y":258.71},{"x":256.05,"y":252.64},{"x":281.82,"y":256.88},{"x":306.76,"y":276.94},{"x":282.72,"y":282.49},{"x":256.76,"y":285.74},{"x":229.65,"y":283.95},{"x":259.16,"y":268.23},{"x":166.5,"y":204.38},{"x":196.96,"y":155.19},{"x":243.22,"y":140.98},{"x":288.41,"y":145.36},{"x":325.02,"y":177.83},{"x":285.73,"y":177.86},{"x":243.88,"y":178.49},{"x":203.67,"y":186.88},{"x":449.64,"y":277.07},{"x":474.26,"y":257.01},{"x":498.82,"y":251.22},{"x":524.71,"y":257.01},{"x":549.36,"y":275.37},{"x":525.74,"y":282.47},{"x":499.74,"y":284.38},{"x":473.84,"y":282.16},{"x":497.86,"y":266.87},{"x":426.02,"y":177.95},{"x":463.35,"y":144.85},{"x":507.91,"y":140.87},{"x":553.53,"y":155.41},{"x":584.24,"y":201.54},{"x":547.02,"y":185.44},{"x":507.44,"y":177.52},{"x":465.91,"y":177.42},{"x":343.82,"y":275.98},{"x":332.28,"y":320.05},{"x":320.75,"y":365.41},{"x":299.75,"y":424.32},{"x":341.12,"y":419.04},{"x":417.78,"y":418.48},{"x":458.55,"y":422.75},{"x":437.07,"y":364.65},{"x":425.61,"y":320.02},{"x":413.36,"y":275.56},{"x":380.01,"y":376.35},{"x":282.51,"y":545.64},{"x":324.84,"y":507.23},{"x":379.74,"y":500.41},{"x":435.63,"y":506.83},{"x":477.78,"y":544.21},{"x":434.46,"y":567.56},{"x":380.8,"y":575.73},{"x":326.68,"y":568.67},{"x":329.97,"y":530.78},{"x":379.81,"y":528.55},{"x":431.43,"y":530.06},{"x":429.89,"y":542.01},{"x":380.64,"y":541.68},{"x":331.8,"y":543.3}]}]}
     */

    private int error_code;
    private String error_msg;
    private long log_id;
    private int timestamp;
    private int cached;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getCached() {
        return cached;
    }

    public void setCached(int cached) {
        this.cached = cached;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * thresholds : {"frr_1e-4":0.05,"frr_1e-3":0.3,"frr_1e-2":0.9}
         * face_liveness : 1
         * face_list : [{"face_token":"54a0acd9361d3178930d8d05fcb65e53","location":{"left":105.58,"top":141.85,"width":540,"height":586,"rotation":0},"face_probability":1,"angle":{"yaw":2.55,"pitch":-4.55,"roll":-3.8},"liveness":{"livemapscore":1},"age":35,"beauty":43.42,"expression":{"type":"none","probability":1},"face_shape":{"type":"square","probability":0.51},"gender":{"type":"male","probability":1},"glasses":{"type":"common","probability":1},"race":{"type":"yellow","probability":1},"landmark":[{"x":259.16,"y":268.23},{"x":497.86,"y":266.87},{"x":380.01,"y":376.35},{"x":380.43,"y":538.05}],"landmark72":[{"x":106.12,"y":323.4},{"x":106.93,"y":411.38},{"x":115.81,"y":498.75},{"x":140.04,"y":585.8},{"x":206.79,"y":669.9},{"x":295.47,"y":714.04},{"x":383.81,"y":726.49},{"x":470.8,"y":712.03},{"x":558.97,"y":664.13},{"x":620.71,"y":579.08},{"x":641.45,"y":490.68},{"x":647.43,"y":402.17},{"x":646.08,"y":315.71},{"x":205.34,"y":277.22},{"x":230.2,"y":258.71},{"x":256.05,"y":252.64},{"x":281.82,"y":256.88},{"x":306.76,"y":276.94},{"x":282.72,"y":282.49},{"x":256.76,"y":285.74},{"x":229.65,"y":283.95},{"x":259.16,"y":268.23},{"x":166.5,"y":204.38},{"x":196.96,"y":155.19},{"x":243.22,"y":140.98},{"x":288.41,"y":145.36},{"x":325.02,"y":177.83},{"x":285.73,"y":177.86},{"x":243.88,"y":178.49},{"x":203.67,"y":186.88},{"x":449.64,"y":277.07},{"x":474.26,"y":257.01},{"x":498.82,"y":251.22},{"x":524.71,"y":257.01},{"x":549.36,"y":275.37},{"x":525.74,"y":282.47},{"x":499.74,"y":284.38},{"x":473.84,"y":282.16},{"x":497.86,"y":266.87},{"x":426.02,"y":177.95},{"x":463.35,"y":144.85},{"x":507.91,"y":140.87},{"x":553.53,"y":155.41},{"x":584.24,"y":201.54},{"x":547.02,"y":185.44},{"x":507.44,"y":177.52},{"x":465.91,"y":177.42},{"x":343.82,"y":275.98},{"x":332.28,"y":320.05},{"x":320.75,"y":365.41},{"x":299.75,"y":424.32},{"x":341.12,"y":419.04},{"x":417.78,"y":418.48},{"x":458.55,"y":422.75},{"x":437.07,"y":364.65},{"x":425.61,"y":320.02},{"x":413.36,"y":275.56},{"x":380.01,"y":376.35},{"x":282.51,"y":545.64},{"x":324.84,"y":507.23},{"x":379.74,"y":500.41},{"x":435.63,"y":506.83},{"x":477.78,"y":544.21},{"x":434.46,"y":567.56},{"x":380.8,"y":575.73},{"x":326.68,"y":568.67},{"x":329.97,"y":530.78},{"x":379.81,"y":528.55},{"x":431.43,"y":530.06},{"x":429.89,"y":542.01},{"x":380.64,"y":541.68},{"x":331.8,"y":543.3}]}]
         */

        private ThresholdsBean thresholds;
        private int face_liveness;
        private List<FaceListBean> face_list;

        public ThresholdsBean getThresholds() {
            return thresholds;
        }

        public void setThresholds(ThresholdsBean thresholds) {
            this.thresholds = thresholds;
        }

        public int getFace_liveness() {
            return face_liveness;
        }

        public void setFace_liveness(int face_liveness) {
            this.face_liveness = face_liveness;
        }

        public List<FaceListBean> getFace_list() {
            return face_list;
        }

        public void setFace_list(List<FaceListBean> face_list) {
            this.face_list = face_list;
        }

        public static class ThresholdsBean {
            /**
             * frr_1e-4 : 0.05
             * frr_1e-3 : 0.3
             * frr_1e-2 : 0.9
             */

            @SerializedName("frr_1e-4")
            private double frr_1e4;
            @SerializedName("frr_1e-3")
            private double frr_1e3;
            @SerializedName("frr_1e-2")
            private double frr_1e2;

            public double getFrr_1e4() {
                return frr_1e4;
            }

            public void setFrr_1e4(double frr_1e4) {
                this.frr_1e4 = frr_1e4;
            }

            public double getFrr_1e3() {
                return frr_1e3;
            }

            public void setFrr_1e3(double frr_1e3) {
                this.frr_1e3 = frr_1e3;
            }

            public double getFrr_1e2() {
                return frr_1e2;
            }

            public void setFrr_1e2(double frr_1e2) {
                this.frr_1e2 = frr_1e2;
            }
        }

        public static class FaceListBean {
            /**
             * face_token : 54a0acd9361d3178930d8d05fcb65e53
             * location : {"left":105.58,"top":141.85,"width":540,"height":586,"rotation":0}
             * face_probability : 1
             * angle : {"yaw":2.55,"pitch":-4.55,"roll":-3.8}
             * liveness : {"livemapscore":1}
             * age : 35
             * beauty : 43.42
             * expression : {"type":"none","probability":1}
             * face_shape : {"type":"square","probability":0.51}
             * gender : {"type":"male","probability":1}
             * glasses : {"type":"common","probability":1}
             * race : {"type":"yellow","probability":1}
             * landmark : [{"x":259.16,"y":268.23},{"x":497.86,"y":266.87},{"x":380.01,"y":376.35},{"x":380.43,"y":538.05}]
             * landmark72 : [{"x":106.12,"y":323.4},{"x":106.93,"y":411.38},{"x":115.81,"y":498.75},{"x":140.04,"y":585.8},{"x":206.79,"y":669.9},{"x":295.47,"y":714.04},{"x":383.81,"y":726.49},{"x":470.8,"y":712.03},{"x":558.97,"y":664.13},{"x":620.71,"y":579.08},{"x":641.45,"y":490.68},{"x":647.43,"y":402.17},{"x":646.08,"y":315.71},{"x":205.34,"y":277.22},{"x":230.2,"y":258.71},{"x":256.05,"y":252.64},{"x":281.82,"y":256.88},{"x":306.76,"y":276.94},{"x":282.72,"y":282.49},{"x":256.76,"y":285.74},{"x":229.65,"y":283.95},{"x":259.16,"y":268.23},{"x":166.5,"y":204.38},{"x":196.96,"y":155.19},{"x":243.22,"y":140.98},{"x":288.41,"y":145.36},{"x":325.02,"y":177.83},{"x":285.73,"y":177.86},{"x":243.88,"y":178.49},{"x":203.67,"y":186.88},{"x":449.64,"y":277.07},{"x":474.26,"y":257.01},{"x":498.82,"y":251.22},{"x":524.71,"y":257.01},{"x":549.36,"y":275.37},{"x":525.74,"y":282.47},{"x":499.74,"y":284.38},{"x":473.84,"y":282.16},{"x":497.86,"y":266.87},{"x":426.02,"y":177.95},{"x":463.35,"y":144.85},{"x":507.91,"y":140.87},{"x":553.53,"y":155.41},{"x":584.24,"y":201.54},{"x":547.02,"y":185.44},{"x":507.44,"y":177.52},{"x":465.91,"y":177.42},{"x":343.82,"y":275.98},{"x":332.28,"y":320.05},{"x":320.75,"y":365.41},{"x":299.75,"y":424.32},{"x":341.12,"y":419.04},{"x":417.78,"y":418.48},{"x":458.55,"y":422.75},{"x":437.07,"y":364.65},{"x":425.61,"y":320.02},{"x":413.36,"y":275.56},{"x":380.01,"y":376.35},{"x":282.51,"y":545.64},{"x":324.84,"y":507.23},{"x":379.74,"y":500.41},{"x":435.63,"y":506.83},{"x":477.78,"y":544.21},{"x":434.46,"y":567.56},{"x":380.8,"y":575.73},{"x":326.68,"y":568.67},{"x":329.97,"y":530.78},{"x":379.81,"y":528.55},{"x":431.43,"y":530.06},{"x":429.89,"y":542.01},{"x":380.64,"y":541.68},{"x":331.8,"y":543.3}]
             */

            private String face_token;
            private LocationBean location;
            private int face_probability;
            private AngleBean angle;
            private LivenessBean liveness;
            private int age;
            private double beauty;
            private ExpressionBean expression;
            private FaceShapeBean face_shape;
            private GenderBean gender;
            private GlassesBean glasses;
            private RaceBean race;
            private List<LandmarkBean> landmark;
            private List<Landmark72Bean> landmark72;

            public String getFace_token() {
                return face_token;
            }

            public void setFace_token(String face_token) {
                this.face_token = face_token;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public int getFace_probability() {
                return face_probability;
            }

            public void setFace_probability(int face_probability) {
                this.face_probability = face_probability;
            }

            public AngleBean getAngle() {
                return angle;
            }

            public void setAngle(AngleBean angle) {
                this.angle = angle;
            }

            public LivenessBean getLiveness() {
                return liveness;
            }

            public void setLiveness(LivenessBean liveness) {
                this.liveness = liveness;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public double getBeauty() {
                return beauty;
            }

            public void setBeauty(double beauty) {
                this.beauty = beauty;
            }

            public ExpressionBean getExpression() {
                return expression;
            }

            public void setExpression(ExpressionBean expression) {
                this.expression = expression;
            }

            public FaceShapeBean getFace_shape() {
                return face_shape;
            }

            public void setFace_shape(FaceShapeBean face_shape) {
                this.face_shape = face_shape;
            }

            public GenderBean getGender() {
                return gender;
            }

            public void setGender(GenderBean gender) {
                this.gender = gender;
            }

            public GlassesBean getGlasses() {
                return glasses;
            }

            public void setGlasses(GlassesBean glasses) {
                this.glasses = glasses;
            }

            public RaceBean getRace() {
                return race;
            }

            public void setRace(RaceBean race) {
                this.race = race;
            }

            public List<LandmarkBean> getLandmark() {
                return landmark;
            }

            public void setLandmark(List<LandmarkBean> landmark) {
                this.landmark = landmark;
            }

            public List<Landmark72Bean> getLandmark72() {
                return landmark72;
            }

            public void setLandmark72(List<Landmark72Bean> landmark72) {
                this.landmark72 = landmark72;
            }

            public static class LocationBean {
                /**
                 * left : 105.58
                 * top : 141.85
                 * width : 540
                 * height : 586
                 * rotation : 0
                 */

                private double left;
                private double top;
                private int width;
                private int height;
                private int rotation;

                public double getLeft() {
                    return left;
                }

                public void setLeft(double left) {
                    this.left = left;
                }

                public double getTop() {
                    return top;
                }

                public void setTop(double top) {
                    this.top = top;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getRotation() {
                    return rotation;
                }

                public void setRotation(int rotation) {
                    this.rotation = rotation;
                }
            }

            public static class AngleBean {
                /**
                 * yaw : 2.55
                 * pitch : -4.55
                 * roll : -3.8
                 */

                private double yaw;
                private double pitch;
                private double roll;

                public double getYaw() {
                    return yaw;
                }

                public void setYaw(double yaw) {
                    this.yaw = yaw;
                }

                public double getPitch() {
                    return pitch;
                }

                public void setPitch(double pitch) {
                    this.pitch = pitch;
                }

                public double getRoll() {
                    return roll;
                }

                public void setRoll(double roll) {
                    this.roll = roll;
                }
            }

            public static class LivenessBean {
                /**
                 * livemapscore : 1
                 */

                private int livemapscore;

                public int getLivemapscore() {
                    return livemapscore;
                }

                public void setLivemapscore(int livemapscore) {
                    this.livemapscore = livemapscore;
                }
            }

            public static class ExpressionBean {
                /**
                 * type : none
                 * probability : 1
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class FaceShapeBean {
                /**
                 * type : square
                 * probability : 0.51
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class GenderBean {
                /**
                 * type : male
                 * probability : 1
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class GlassesBean {
                /**
                 * type : common
                 * probability : 1
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class RaceBean {
                /**
                 * type : yellow
                 * probability : 1
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class LandmarkBean {
                /**
                 * x : 259.16
                 * y : 268.23
                 */

                private double x;
                private double y;

                public double getX() {
                    return x;
                }

                public void setX(double x) {
                    this.x = x;
                }

                public double getY() {
                    return y;
                }

                public void setY(double y) {
                    this.y = y;
                }
            }

            public static class Landmark72Bean {
                /**
                 * x : 106.12
                 * y : 323.4
                 */

                private double x;
                private double y;

                public double getX() {
                    return x;
                }

                public void setX(double x) {
                    this.x = x;
                }

                public double getY() {
                    return y;
                }

                public void setY(double y) {
                    this.y = y;
                }
            }
        }
    }
}
