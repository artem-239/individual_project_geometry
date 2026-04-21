package ru.artem.project;
import java.util.*;

public class CalculateLength {
    private Point pointCenter = new Point(0, 0);
    private Point point;
    private OurRectangle ourRectangle;

    private double V1x;
    private double V1y;
    private double V2x;
    private double V2y;
    private double V3x;
    private double V3y;
    private double V4x;
    private double V4y;

    private double R12x;
    private double R12y;
    private double R23x;
    private double R23y;
    private double R34x;
    private double R34y;
    private double R41x;
    private double R41y;

    private double lineParameterVertex12;
    private double lineParameterVertex23;
    private double lineParameterVertex34;
    private double lineParameterVertex41;
    private double lineParameter;

    private double lineParameter12;
    private double lineParameter23;
    private double lineParameter34;
    private double lineParameter41;

    public boolean checkIsPointInRectangle(Point point, OurRectangle ourRectangle){
        List<Point> pointList = ourRectangle.getAllPoints();
         Point vertex1 = pointList.get(0);
         Point vertex2 = pointList.get(1);
         Point vertex3 = pointList.get(2);
         Point vertex4 = pointList.get(3);

         //Строим вектора от точки до вершин
        V1x = point.getX() - vertex1.getX();
        V1y = point.getY() - vertex1.getY();

        V2x = point.getX() - vertex2.getX();
        V2y = point.getY() - vertex2.getY();

        V3x = point.getX() - vertex3.getX();
        V3y = point.getY() - vertex3.getY();

        V4x = point.getX() - vertex4.getX();
        V4y = point.getY() - vertex4.getY();

        //Находим их длину
        R12x = vertex2.getX() - vertex1.getX();
        R12y = vertex2.getY() - vertex1.getY();

        R23x = vertex3.getX() - vertex2.getX();
        R23y = vertex3.getY() - vertex2.getY();

        R34x = vertex4.getX() - vertex3.getX();
        R34y = vertex4.getY() - vertex3.getY();

        R41x = vertex1.getX() - vertex4.getX();
        R41y = vertex1.getY() - vertex4.getY();

        double vectorProduct1 = R12x * V1y - R12y * V1x;
        double vectorProduct2 = R23x * V2y - R23y * V2x;
        double vectorProduct3 = R34x * V3y - R34y * V3x;
        double vectorProduct4 = R41x * V4y - R41y * V4x;

        if (vectorProduct1 >= 0 && vectorProduct2 >= 0 && vectorProduct3 >= 0 && vectorProduct4 >= 0){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Возвращает длину отрезка в прямоугольнике
     * @param point
     * @param ourRectangle
     * @return
     */
    public double getLength(Point point, OurRectangle ourRectangle) {

        List<Point> pointList = ourRectangle.getAllPoints();
        Point vertex1 = pointList.get(0);
        Point vertex2 = pointList.get(1);
        Point vertex3 = pointList.get(2);
        Point vertex4 = pointList.get(3);

        double lineLength = 0;

        /*
        начальная и конечная точки лежат внутри прямоугольника
         */
        if (checkIsPointInRectangle(pointCenter, ourRectangle) && checkIsPointInRectangle(point, ourRectangle)) {
            //Длина - просто наш отрезок
            double lineLengthX = point.getX() - pointCenter.getX();
            double lineLengthY = point.getY() - pointCenter.getY();

            lineLength = Math.sqrt(Math.pow(lineLengthX, 2) + Math.pow(lineLengthY, 2));

            return lineLength;
        }
        /*
         центральная точка лежит, точка не лежит
         */
        if (checkIsPointInRectangle(pointCenter, ourRectangle) && !checkIsPointInRectangle(point, ourRectangle)) {
            //Ищем длину отрезка
            double ourLineLengthX = point.getX() - pointCenter.getX();
            double ourLineLengthY = point.getY() - pointCenter.getY();

            //Ищем длину стороны образованной 1 и 2 вершиной
            R12x = vertex2.getX() - vertex1.getX();
            R12y = vertex2.getY() - vertex1.getY();
            //Ищем определитель системы
            double DD = -1 * ourLineLengthX * R12y + ourLineLengthY * R12x;

            //Ищем частные определители
            double DA = -1 * (vertex1.getX() - pointCenter.getX()) * R12y + (vertex1.getY() - pointCenter.getY()) * R12x;
            double DB = ourLineLengthX * (vertex1.getY() - pointCenter.getY()) - ourLineLengthY * (vertex1.getX() - pointCenter.getX());

            //Ищем параметр точки пересечения отрезка и стороны
            lineParameter = DA / DD;
            lineParameterVertex12 = DB / DD;

            //проверка того, что пересекаются отрезки, а не линии, нахождение длины пересечения
            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex12 >= 0 && lineParameterVertex12 <= 1) {
                //Ищем координаты 1 точки
                double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Вторая точка это точка центра
                double lineLengthX = pX - pointCenter.getX();
                double lineLengthY = pY - pointCenter.getY();
                lineLength = Math.sqrt(Math.pow(lineLengthX, 2) + Math.pow(lineLengthY, 2));
                return lineLength;
            }

            //Ищем длину стороны образованной 2 и 3 вершиной
            R23x = vertex3.getX() - vertex2.getX();
            R23y = vertex3.getY() - vertex2.getY();

            //Ищем определитель системы
            DD = -1 * ourLineLengthX * R23y + ourLineLengthY * R23x;

            //Ищем частные определители
            DA = -1 * (vertex2.getX() - pointCenter.getX()) * R23y + (vertex2.getY() - pointCenter.getY()) * R23x;
            DB = ourLineLengthX * (vertex2.getY() - pointCenter.getY()) - ourLineLengthY * (vertex2.getX() - pointCenter.getX());

            //Ищем параметр точки пересечения отрезка и стороны
            lineParameter = DA / DD;
            lineParameterVertex23 = DB / DD;

            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex23 >= 0 && lineParameterVertex23 <= 1) {
                //Ищем координаты 1 точки
                double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Вторая точка это точка центра
                double lineLengthX = pX - pointCenter.getX();
                double lineLengthY = pY - pointCenter.getY();
                lineLength = Math.sqrt(Math.pow(lineLengthX, 2) + Math.pow(lineLengthY, 2));
                return lineLength;
            }

            R34x = vertex4.getX() - vertex3.getX();
            R34y = vertex4.getY() - vertex3.getY();

            DD = -1 * ourLineLengthX * R34y + ourLineLengthY * R34x;
            DA = -1 * (vertex3.getX() - pointCenter.getX()) * R34y + (vertex3.getY() - pointCenter.getY()) * R34x;
            DB = ourLineLengthX * (vertex3.getY() - pointCenter.getY()) - ourLineLengthY * (vertex3.getX() - pointCenter.getX());

            lineParameter = DA / DD;
            lineParameterVertex34 = DB / DD;

            //проверка того, что пересекаются отрезки, а не линии, нахождение длины пересечения
            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex34 >= 0 && lineParameterVertex34 <= 1) {
                //Ищем координаты 1 точки
                double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Вторая точка это точка центра
                double lineLengthX = pX - pointCenter.getX();
                double lineLengthY = pY - pointCenter.getY();
                lineLength = Math.sqrt(Math.pow(lineLengthX, 2) + Math.pow(lineLengthY, 2));
                return lineLength;
            }

            R41x = vertex1.getX() - vertex4.getX();
            R41y = vertex1.getY() - vertex4.getY();

            DD = -1 * ourLineLengthX * R41y + ourLineLengthY * R41x;
            DA = -1 * (vertex4.getX() - pointCenter.getX()) * R41y + (vertex4.getY() - pointCenter.getY()) * R41x;
            DB = ourLineLengthX * (vertex4.getY() - pointCenter.getY()) - ourLineLengthY * (vertex4.getX() - pointCenter.getX());

            lineParameter = DA / DD;
            lineParameterVertex41 = DB / DD;

            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex41 >= 0 && lineParameterVertex41 <= 1) {
                //Ищем координаты 1 точки
                double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Вторая точка это точка центра
                double lineLengthX = pX - pointCenter.getX();
                double lineLengthY = pY - pointCenter.getY();
                lineLength = Math.sqrt(Math.pow(lineLengthX, 2) + Math.pow(lineLengthY, 2));
                return lineLength;
            }
        }
        /**
         * центральная точка не лежит, точка лежит
         */
        if (!checkIsPointInRectangle(pointCenter, ourRectangle) && checkIsPointInRectangle(point, ourRectangle)) {
            double ourLineLengthX = point.getX() - pointCenter.getX();
            double ourLineLengthY = point.getY() - pointCenter.getY();

            R12x = vertex2.getX() - vertex1.getX();
            R12y = vertex2.getY() - vertex1.getY();

            double DD = -1 * ourLineLengthX * R12y + ourLineLengthY * R12x;
            double DA = -1 * (vertex1.getX() - pointCenter.getX()) * R12y + (vertex1.getY() - pointCenter.getY()) * R12x;
            double DB = ourLineLengthX * (vertex1.getY() - pointCenter.getY()) - ourLineLengthY * (vertex1.getX() - pointCenter.getX());
            lineParameter = DA / DD;
            lineParameterVertex12 = DB / DD;

            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex12 >= 0 && lineParameterVertex12 <= 1) {
                //Ищем координаты 1 точки
                double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Вторая точка - конечная точка
                double lineLengthX = point.getX() - pX;
                double lineLengthY = point.getY() - pY;
                lineLength = Math.sqrt(Math.pow(lineLengthX, 2) + Math.pow(lineLengthY, 2));
                return lineLength;
            }

            R23x = vertex3.getX() - vertex2.getX();
            R23y = vertex3.getY() - vertex2.getY();

            DD = -1 * ourLineLengthX * R23y + ourLineLengthY * R23x;
            DA = -1 * (vertex2.getX() - pointCenter.getX()) * R23y + (vertex2.getY() - pointCenter.getY()) * R23x;
            DB = ourLineLengthX * (vertex2.getY() - pointCenter.getY()) - ourLineLengthY * (vertex2.getX() - pointCenter.getX());

            lineParameter = DA / DD;
            lineParameterVertex23 = DB / DD;

            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex23 >= 0 && lineParameterVertex23 <= 1) {
                //Ищем координаты 1 точки
                double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Вторая точка - конечная точка
                double lineLengthX = point.getX() - pX;
                double lineLengthY = point.getY() - pY;
                lineLength = Math.sqrt(Math.pow(lineLengthX, 2) + Math.pow(lineLengthY, 2));
                return lineLength;
            }

            R34x = vertex4.getX() - vertex3.getX();
            R34y = vertex4.getY() - vertex3.getY();

            DD = -1 * ourLineLengthX * R34y + ourLineLengthY * R34x;
            DA = -1 * (vertex3.getX() - pointCenter.getX()) * R34y + (vertex3.getY() - pointCenter.getY()) * R34x;
            DB = ourLineLengthX * (vertex3.getY() - pointCenter.getY()) - ourLineLengthY * (vertex3.getX() - pointCenter.getX());

            lineParameter = DA / DD;
            lineParameterVertex34 = DB / DD;

            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex34 >= 0 && lineParameterVertex34 <= 1) {
                //Ищем координаты 1 точки
                double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Вторая точка - конечная точка
                double lineLengthX = point.getX() - pX;
                double lineLengthY = point.getY() - pY;
                lineLength = Math.sqrt(Math.pow(lineLengthX, 2) + Math.pow(lineLengthY, 2));
                return lineLength;
            }

            R41x = vertex1.getX() - vertex4.getX();
            R41y = vertex1.getY() - vertex4.getY();

            DD = -1 * ourLineLengthX * R41y + ourLineLengthY * R41x;
            DA = -1 * (vertex4.getX() - pointCenter.getX()) * R41y + (vertex4.getY() - pointCenter.getY()) * R41x;
            DB = ourLineLengthX * (vertex4.getY() - pointCenter.getY()) - ourLineLengthY * (vertex4.getX() - pointCenter.getX());

            lineParameter = DA / DD;
            lineParameterVertex41 = DB / DD;

            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex41 >= 0 && lineParameterVertex41 <= 1) {
                //Ищем координаты 1 точки
                double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Вторая точка - конечная точка
                double lineLengthX = point.getX() - pX;
                double lineLengthY = point.getY() - pY;
                lineLength = Math.sqrt(Math.pow(lineLengthX, 2) + Math.pow(lineLengthY, 2));
                return lineLength;
            }
        }

        //начало и конец линии не лежат внутри прямоугольника
        if (!checkIsPointInRectangle(pointCenter, ourRectangle) && !checkIsPointInRectangle(point, ourRectangle)) {
            //Создание хранилища точек пересечения, так как незнаем с какой стороной пересекается отрезок и пересекается ли
            Map<Integer, String> mapCache = new HashMap<Integer, String>();
            double ourLineLengthX = point.getX() - pointCenter.getX();
            double ourLineLengthY = point.getY() - pointCenter.getY();

            R12x = vertex2.getX() - vertex1.getX();
            R12y = vertex2.getY() - vertex1.getY();

            double DD = -1 * ourLineLengthX * R12y + ourLineLengthY * R12x;
            double DA = -1 * (vertex1.getX() - pointCenter.getX()) * R12y + (vertex1.getY() - pointCenter.getY()) * R12x;
            double DB = ourLineLengthX * (vertex1.getY() - pointCenter.getY()) - ourLineLengthY * (vertex1.getX() - pointCenter.getX());

            lineParameter = DA / DD;
            lineParameterVertex12 = DB / DD;

            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex12 >= 0 && lineParameterVertex12 <= 1) {
                //Ищем координаты точек пересечения
                double p1X = pointCenter.getX() + lineParameter * ourLineLengthX;
                double p1Y = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Заносим в мапу
                mapCache.put(1, String.valueOf(p1X) + "; " + String.valueOf(p1Y));
            }

            R23x = vertex3.getX() - vertex2.getX();
            R23y = vertex3.getY() - vertex2.getY();

            DD = -1 * ourLineLengthX * R23y + ourLineLengthY * R23x;
            DA = -1 * (vertex2.getX() - pointCenter.getX()) * R23y + (vertex2.getY() - pointCenter.getY()) * R23x;
            DB = ourLineLengthX * (vertex2.getY() - pointCenter.getY()) - ourLineLengthY * (vertex2.getX() - pointCenter.getX());

            lineParameter = DA / DD;
            lineParameterVertex23 = DB / DD;

            //Находим, является ли точка пересечения точкой соединения сторон прямоугольника, если да, то не учитываем ее, так как учли ее ранее
            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex23 >= 0 && lineParameterVertex23 <= 1 && !(lineParameterVertex12 == 1 && lineParameterVertex23 == 0)) {
                //Ищем координаты точек пересечения
                double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Заносим в мапу
                if (!mapCache.isEmpty()) {
                    mapCache.put(2, String.valueOf(pX) + "; " + String.valueOf(pY));
                } else {
                    mapCache.put(1, String.valueOf(pX) + "; " + String.valueOf(pY));
                }
            }

            R34x = vertex4.getX() - vertex3.getX();
            R34y = vertex4.getY() - vertex3.getY();

            DD = -1 * ourLineLengthX * R34y + ourLineLengthY * R34x;
            DA = -1 * (vertex3.getX() - pointCenter.getX()) * R34y + (vertex3.getY() - pointCenter.getY()) * R34x;
            DB = ourLineLengthX * (vertex3.getY() - pointCenter.getY()) - ourLineLengthY * (vertex3.getX() - pointCenter.getX());

            lineParameter = DA / DD;
            lineParameterVertex34 = DB / DD;

            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex34 >= 0 && lineParameterVertex34 <= 1 && !(lineParameterVertex23 == 1 && lineParameterVertex34 == 0)) {
                //Ищем координаты точек пересечения
                double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Заносим в мапу
                if (!mapCache.isEmpty()) {
                    mapCache.put(2, String.valueOf(pX) + "; " + String.valueOf(pY));
                } else {
                    mapCache.put(1, String.valueOf(pX) + "; " + String.valueOf(pY));
                }
            }

            R41x = vertex1.getX() - vertex4.getX();
            R41y = vertex1.getY() - vertex4.getY();

            DD = -1 * ourLineLengthX * R41y + ourLineLengthY * R41x;
            DA = -1 * (vertex4.getX() - pointCenter.getX()) * R41y + (vertex4.getY() - pointCenter.getY()) * R41x;
            DB = ourLineLengthX * (vertex4.getY() - pointCenter.getY()) - ourLineLengthY * (vertex4.getX() - pointCenter.getX());

            lineParameter = DA / DD;
            lineParameterVertex41 = DB / DD;

            if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex41 >= 0 && lineParameterVertex41 <= 1 && !(lineParameterVertex34 == 1 && lineParameterVertex41 == 0) && !(lineParameterVertex12 == 0 && lineParameterVertex41 == 1)) {
                //Ищем координаты точек пересечения
                double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                //Заносим в мапу
                if (!mapCache.isEmpty()) {
                    mapCache.put(2, String.valueOf(pX) + "; " + String.valueOf(pY));
                } else {
                    mapCache.put(1, String.valueOf(pX) + "; " + String.valueOf(pY));
                }
            }

            if (mapCache.isEmpty()) {
                //нет пересечений
                return 0;
            } else if (mapCache.size() == 2) {
                //вычисляем длину отрезка
                String p1 = mapCache.get(1);
                String p2 = mapCache.get(2);
                StringTokenizer tokenizer = new StringTokenizer(p1,";");
                double p1x = Double.valueOf(tokenizer.nextToken());
                double p1y = Double.valueOf(tokenizer.nextToken());
                tokenizer = new StringTokenizer(p2,";");
                double p2x = Double.valueOf(tokenizer.nextToken());
                double p2y = Double.valueOf(tokenizer.nextToken());
                double lineLengthX = p2x - p1x;
                double lineLengthY = p2y - p1y;
                lineLength = Math.sqrt(Math.pow(lineLengthX, 2) + Math.pow(lineLengthY, 2));
                return lineLength;
            } else {
                //ошибка
            }
        }
        return lineLength;
    }

     public List<Point> getIntersectionPoints(Point point, OurRectangle ourRectangle){

         List<Point> pointList = ourRectangle.getAllPoints();
         Point vertex1 = pointList.get(0);
         Point vertex2 = pointList.get(1);
         Point vertex3 = pointList.get(2);
         Point vertex4 = pointList.get(3);

         List<Point> points = new ArrayList<>();

         /*
         Начальная и конечная точки лежат внутри прямоугольника
          */
         if (checkIsPointInRectangle(pointCenter, ourRectangle) && checkIsPointInRectangle(point, ourRectangle)) {
            points.add(point);
            points.add(pointCenter);
            return points;
         }
        /*
         центральная точка лежит, точка не лежит
         */

         //Начальная точка лежит, конечная нет
         if (checkIsPointInRectangle(pointCenter, ourRectangle) && !checkIsPointInRectangle(point, ourRectangle)) {
             double ourLineLengthX = point.getX() - pointCenter.getX();
             double ourLineLengthY = point.getY() - pointCenter.getY();

             R12x = vertex2.getX() - vertex1.getX();
             R12y = vertex2.getY() - vertex1.getY();

             double DD = -1 * ourLineLengthX * R12y + ourLineLengthY * R12x;

             double DA = -1 * (vertex1.getX() - pointCenter.getX()) * R12y + (vertex1.getY() - pointCenter.getY()) * R12x;
             double DB = ourLineLengthX * (vertex1.getY() - pointCenter.getY()) - ourLineLengthY * (vertex1.getX() - pointCenter.getX());
             lineParameter = DA / DD;
             lineParameterVertex12 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex12 >= 0 && lineParameterVertex12 <= 1) {
                 double intersectionPointX = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double intersectionPointY = pointCenter.getY() + lineParameter * ourLineLengthY;
                 Point intersectionPoint = new Point(intersectionPointX, intersectionPointY);
                 points.add(pointCenter);
                 points.add(intersectionPoint);
                 return points;
             }

             R23x = vertex3.getX() - vertex2.getX();
             R23y = vertex3.getY() - vertex2.getY();

             DD = -1 * ourLineLengthX * R23y + ourLineLengthY * R23x;
             DA = -1 * (vertex2.getX() - pointCenter.getX()) * R23y + (vertex2.getY() - pointCenter.getY()) * R23x;
             DB = ourLineLengthX * (vertex2.getY() - pointCenter.getY()) - ourLineLengthY * (vertex2.getX() - pointCenter.getX());

             lineParameter = DA / DD;
             lineParameterVertex23 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex23 >= 0 && lineParameterVertex23 <= 1) {
                 double intersectionPointX = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double intersectionPointY = pointCenter.getY() + lineParameter * ourLineLengthY;
                 Point intersectionPoint = new Point(intersectionPointX, intersectionPointY);
                 points.add(pointCenter);
                 points.add(intersectionPoint);
                 return points;
             }

             R34x = vertex4.getX() - vertex3.getX();
             R34y = vertex4.getY() - vertex3.getY();

             DD = -1 * ourLineLengthX * R34y + ourLineLengthY * R34x;
             DA = -1 * (vertex3.getX() - pointCenter.getX()) * R34y + (vertex3.getY() - pointCenter.getY()) * R34x;
             DB = ourLineLengthX * (vertex3.getY() - pointCenter.getY()) - ourLineLengthY * (vertex3.getX() - pointCenter.getX());

             lineParameter = DA / DD;
             lineParameterVertex34 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex34 >= 0 && lineParameterVertex34 <= 1) {
                 double intersectionPointX = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double intersectionPointY = pointCenter.getY() + lineParameter * ourLineLengthY;
                 Point intersectionPoint = new Point(intersectionPointX, intersectionPointY);
                 points.add(pointCenter);
                 points.add(intersectionPoint);
                 return points;
             }

             R41x = vertex1.getX() - vertex4.getX();
             R41y = vertex1.getY() - vertex4.getY();

             DD = -1 * ourLineLengthX * R41y + ourLineLengthY * R41x;
             DA = -1 * (vertex4.getX() - pointCenter.getX()) * R41y + (vertex4.getY() - pointCenter.getY()) * R41x;
             DB = ourLineLengthX * (vertex4.getY() - pointCenter.getY()) - ourLineLengthY * (vertex4.getX() - pointCenter.getX());

             lineParameter = DA / DD;
             lineParameterVertex41 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex41 >= 0 && lineParameterVertex41 <= 1) {
                 double intersectionPointX = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double intersectionPointY = pointCenter.getY() + lineParameter * ourLineLengthY;
                 Point intersectionPoint = new Point(intersectionPointX, intersectionPointY);
                 points.add(pointCenter);
                 points.add(intersectionPoint);
                 return points;
             }
         }
         /**
          * центральная точка не лежит внутри прямоугольника, конечная точка лежит внутри прямоугольника
          */
         if (!checkIsPointInRectangle(pointCenter, ourRectangle) && checkIsPointInRectangle(point, ourRectangle)) {
             double ourLineLengthX = point.getX() - pointCenter.getX();
             double ourLineLengthY = point.getY() - pointCenter.getY();

             R12x = vertex2.getX() - vertex1.getX();
             R12y = vertex2.getY() - vertex1.getY();

             double DD = -1 * ourLineLengthX * R12y + ourLineLengthY * R12x;
             double DA = -1 * (vertex1.getX() - pointCenter.getX()) * R12y + (vertex1.getY() - pointCenter.getY()) * R12x;
             double DB = ourLineLengthX * (vertex1.getY() - pointCenter.getY()) - ourLineLengthY * (vertex1.getX() - pointCenter.getX());
             lineParameter = DA / DD;
             lineParameterVertex12 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex12 >= 0 && lineParameterVertex12 <= 1) {
                 double intersectionPointX = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double intersectionPointY = pointCenter.getY() + lineParameter * ourLineLengthY;
                 Point intersectionPoint = new Point(intersectionPointX, intersectionPointY);
                 points.add(intersectionPoint);
                 points.add(point);
                 return points;
             }

             R23x = vertex3.getX() - vertex2.getX();
             R23y = vertex3.getY() - vertex2.getY();

             DD = -1 * ourLineLengthX * R23y + ourLineLengthY * R23x;
             DA = -1 * (vertex2.getX() - pointCenter.getX()) * R23y + (vertex2.getY() - pointCenter.getY()) * R23x;
             DB = ourLineLengthX * (vertex2.getY() - pointCenter.getY()) - ourLineLengthY * (vertex2.getX() - pointCenter.getX());

             lineParameter = DA / DD;
             lineParameterVertex23 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex23 >= 0 && lineParameterVertex23 <= 1) {
                 double intersectionPointX = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double intersectionPointY = pointCenter.getY() + lineParameter * ourLineLengthY;
                 Point intersectionPoint = new Point(intersectionPointX, intersectionPointY);
                 points.add(intersectionPoint);
                 points.add(point);
                 return points;
             }

             R34x = vertex4.getX() - vertex3.getX();
             R34y = vertex4.getY() - vertex3.getY();

             DD = -1 * ourLineLengthX * R34y + ourLineLengthY * R34x;
             DA = -1 * (vertex3.getX() - pointCenter.getX()) * R34y + (vertex3.getY() - pointCenter.getY()) * R34x;
             DB = ourLineLengthX * (vertex3.getY() - pointCenter.getY()) - ourLineLengthY * (vertex3.getX() - pointCenter.getX());

             lineParameter = DA / DD;
             lineParameterVertex34 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex34 >= 0 && lineParameterVertex34 <= 1) {
                 double intersectionPointX = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double intersectionPointY = pointCenter.getY() + lineParameter * ourLineLengthY;
                 Point intersectionPoint = new Point(intersectionPointX, intersectionPointY);
                 points.add(intersectionPoint);
                 points.add(point);
                 return points;
             }

             R41x = vertex1.getX() - vertex4.getX();
             R41y = vertex1.getY() - vertex4.getY();

             DD = -1 * ourLineLengthX * R41y + ourLineLengthY * R41x;
             DA = -1 * (vertex4.getX() - pointCenter.getX()) * R41y + (vertex4.getY() - pointCenter.getY()) * R41x;
             DB = ourLineLengthX * (vertex4.getY() - pointCenter.getY()) - ourLineLengthY * (vertex4.getX() - pointCenter.getX());

             lineParameter = DA / DD;
             lineParameterVertex41 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex41 >= 0 && lineParameterVertex41 <= 1) {
                 double intersectionPointX = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double intersectionPointY = pointCenter.getY() + lineParameter * ourLineLengthY;
                 Point intersectionPoint = new Point(intersectionPointX, intersectionPointY);
                 points.add(intersectionPoint);
                 points.add(point);
                 return points;
             }
         }
         //начальная и конечная точки не лежат внутри прямоугольника
         if (!checkIsPointInRectangle(pointCenter, ourRectangle) && !checkIsPointInRectangle(point, ourRectangle)) {
             Map<Integer, String> mapCache = new HashMap<Integer, String>();
             double ourLineLengthX = point.getX() - pointCenter.getX();
             double ourLineLengthY = point.getY() - pointCenter.getY();

             R12x = vertex2.getX() - vertex1.getX();
             R12y = vertex2.getY() - vertex1.getY();

             double DD = -1 * ourLineLengthX * R12y + ourLineLengthY * R12x;
             double DA = -1 * (vertex1.getX() - pointCenter.getX()) * R12y + (vertex1.getY() - pointCenter.getY()) * R12x;
             double DB = ourLineLengthX * (vertex1.getY() - pointCenter.getY()) - ourLineLengthY * (vertex1.getX() - pointCenter.getX());

             lineParameter = DA / DD;
             lineParameterVertex12 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex12 >= 0 && lineParameterVertex12 <= 1) {
                 double p1X = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double p1Y = pointCenter.getY() + lineParameter * ourLineLengthY;
                 mapCache.put(1, String.valueOf(p1X) + "; " + String.valueOf(p1Y));
             }

             R23x = vertex3.getX() - vertex2.getX();
             R23y = vertex3.getY() - vertex2.getY();

             DD = -1 * ourLineLengthX * R23y + ourLineLengthY * R23x;
             DA = -1 * (vertex2.getX() - pointCenter.getX()) * R23y + (vertex2.getY() - pointCenter.getY()) * R23x;
             DB = ourLineLengthX * (vertex2.getY() - pointCenter.getY()) - ourLineLengthY * (vertex2.getX() - pointCenter.getX());

             lineParameter = DA / DD;
             lineParameterVertex23 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex23 >= 0 && lineParameterVertex23 <= 1 && !(lineParameterVertex12 == 1 && lineParameterVertex23 == 0)) {
                 double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                 if (!mapCache.isEmpty()) {
                     mapCache.put(2, String.valueOf(pX) + "; " + String.valueOf(pY));
                 } else {
                     mapCache.put(1, String.valueOf(pX) + "; " + String.valueOf(pY));
                 }
             }

             R34x = vertex4.getX() - vertex3.getX();
             R34y = vertex4.getY() - vertex3.getY();

             DD = -1 * ourLineLengthX * R34y + ourLineLengthY * R34x;
             DA = -1 * (vertex3.getX() - pointCenter.getX()) * R34y + (vertex3.getY() - pointCenter.getY()) * R34x;
             DB = ourLineLengthX * (vertex3.getY() - pointCenter.getY()) - ourLineLengthY * (vertex3.getX() - pointCenter.getX());

             lineParameter = DA / DD;
             lineParameterVertex34 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex34 >= 0 && lineParameterVertex34 <= 1 && !(lineParameterVertex23 == 1 && lineParameterVertex34 == 0)) {
                 double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                 if (!mapCache.isEmpty()) {
                     mapCache.put(2, String.valueOf(pX) + "; " + String.valueOf(pY));
                 } else {
                     mapCache.put(1, String.valueOf(pX) + "; " + String.valueOf(pY));
                 }
             }

             R41x = vertex1.getX() - vertex4.getX();
             R41y = vertex1.getY() - vertex4.getY();

             DD = -1 * ourLineLengthX * R41y + ourLineLengthY * R41x;
             DA = -1 * (vertex4.getX() - pointCenter.getX()) * R41y + (vertex4.getY() - pointCenter.getY()) * R41x;
             DB = ourLineLengthX * (vertex4.getY() - pointCenter.getY()) - ourLineLengthY * (vertex4.getX() - pointCenter.getX());

             lineParameter = DA / DD;
             lineParameterVertex41 = DB / DD;

             if (lineParameter > 0 && lineParameter < 1 && lineParameterVertex41 >= 0 && lineParameterVertex41 <= 1 && !(lineParameterVertex34 == 1 && lineParameterVertex41 == 0) && !(lineParameterVertex12 == 0 && lineParameterVertex41 == 1)) {
                 double pX = pointCenter.getX() + lineParameter * ourLineLengthX;
                 double pY = pointCenter.getY() + lineParameter * ourLineLengthY;
                 if (!mapCache.isEmpty()) {
                     mapCache.put(2, String.valueOf(pX) + "; " + String.valueOf(pY));
                 } else {
                     mapCache.put(1, String.valueOf(pX) + "; " + String.valueOf(pY));
                 }
             }

             if (mapCache.isEmpty()) {
                 //нет пересечений
                 return points;
             } else if (mapCache.size() == 2) {
                 //вычисляем длину отрезка
                 String p1 = mapCache.get(1);
                 String p2 = mapCache.get(2);
                 StringTokenizer tokenizer = new StringTokenizer(p1,";");
                 double firstIntersectionPointX = Double.valueOf(tokenizer.nextToken());
                 double firstIntersectionPointY = Double.valueOf(tokenizer.nextToken());
                 Point firstIntersectionPoint = new Point(firstIntersectionPointX, firstIntersectionPointY);

                 tokenizer = new StringTokenizer(p2,";");
                 double secondIntersectionPointX = Double.valueOf(tokenizer.nextToken());
                 double secondIntersectionPointY = Double.valueOf(tokenizer.nextToken());
                 Point secondIntersectionPoint = new Point(secondIntersectionPointX, secondIntersectionPointY);
                 points.add(firstIntersectionPoint);
                 points.add(secondIntersectionPoint);
                 return points;
             } else {
                 //ошибка
             }
         }
         return points;
     }
}