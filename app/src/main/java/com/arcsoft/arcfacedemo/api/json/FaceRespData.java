package com.arcsoft.arcfacedemo.api.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaceRespData {

    /**
     * error_code : 0
     * error_msg : SUCCESS
     * log_id : 1368654408202810801
     * timestamp : 1550820281
     * cached : 0
     * result : {"thresholds":{"frr_1e-4":0.05,"frr_1e-3":0.3,"frr_1e-2":0.9},"face_liveness":1,"face_list":[{"face_token":"c4c9df1599a9e9ee8993dfd6a449fa06","location":{"left":381.59,"top":1037.69,"width":448,"height":486,"rotation":-1},"face_probability":1,"angle":{"yaw":-3.17,"pitch":3.78,"roll":-4.7},"liveness":{"livemapscore":1},"age":36,"beauty":35.59,"expression":{"type":"none","probability":1},"face_shape":{"type":"square","probability":0.38},"gender":{"type":"male","probability":1},"glasses":{"type":"common","probability":1},"race":{"type":"yellow","probability":1}}]}
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
         * face_list : [{"face_token":"c4c9df1599a9e9ee8993dfd6a449fa06","location":{"left":381.59,"top":1037.69,"width":448,"height":486,"rotation":-1},"face_probability":1,"angle":{"yaw":-3.17,"pitch":3.78,"roll":-4.7},"liveness":{"livemapscore":1},"age":36,"beauty":35.59,"expression":{"type":"none","probability":1},"face_shape":{"type":"square","probability":0.38},"gender":{"type":"male","probability":1},"glasses":{"type":"common","probability":1},"race":{"type":"yellow","probability":1}}]
         */

        private ThresholdsBean thresholds;
        private float face_liveness;
        private List<FaceListBean> face_list;

        public ThresholdsBean getThresholds() {
            return thresholds;
        }

        public void setThresholds(ThresholdsBean thresholds) {
            this.thresholds = thresholds;
        }

        public float getFace_liveness() {
            return face_liveness;
        }

        public void setFace_liveness(float face_liveness) {
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
             * face_token : c4c9df1599a9e9ee8993dfd6a449fa06
             * location : {"left":381.59,"top":1037.69,"width":448,"height":486,"rotation":-1}
             * face_probability : 1
             * angle : {"yaw":-3.17,"pitch":3.78,"roll":-4.7}
             * liveness : {"livemapscore":1}
             * age : 36
             * beauty : 35.59
             * expression : {"type":"none","probability":1}
             * face_shape : {"type":"square","probability":0.38}
             * gender : {"type":"male","probability":1}
             * glasses : {"type":"common","probability":1}
             * race : {"type":"yellow","probability":1}
             */

            private String face_token;
            private LocationBean location;
            private float face_probability;
            private AngleBean angle;
            private LivenessBean liveness;
            private int age;
            private double beauty;
            private ExpressionBean expression;
            private FaceShapeBean face_shape;
            private GenderBean gender;
            private GlassesBean glasses;
            private RaceBean race;

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

            public float getFace_probability() {
                return face_probability;
            }

            public void setFace_probability(float face_probability) {
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

            public static class LocationBean {
                /**
                 * left : 381.59
                 * top : 1037.69
                 * width : 448
                 * height : 486
                 * rotation : -1
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
                 * yaw : -3.17
                 * pitch : 3.78
                 * roll : -4.7
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

                private float livemapscore;

                public float getLivemapscore() {
                    return livemapscore;
                }

                public void setLivemapscore(float livemapscore) {
                    this.livemapscore = livemapscore;
                }
            }

            public static class ExpressionBean {
                /**
                 * type : none
                 * probability : 1
                 */

                private String type;
                private float probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public float getProbability() {
                    return probability;
                }

                public void setProbability(float probability) {
                    this.probability = probability;
                }
            }

            public static class FaceShapeBean {
                /**
                 * type : square
                 * probability : 0.38
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
                private float probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public float getProbability() {
                    return probability;
                }

                public void setProbability(float probability) {
                    this.probability = probability;
                }
            }

            public static class GlassesBean {
                /**
                 * type : common
                 * probability : 1
                 */

                private String type;
                private float probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public float getProbability() {
                    return probability;
                }

                public void setProbability(float probability) {
                    this.probability = probability;
                }
            }

            public static class RaceBean {
                /**
                 * type : yellow
                 * probability : 1
                 */

                private String type;
                private float probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public float getProbability() {
                    return probability;
                }

                public void setProbability(float probability) {
                    this.probability = probability;
                }
            }
        }
    }
}
