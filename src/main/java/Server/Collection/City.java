package Server.Collection;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс города со свойствами id, name, coordiantes, creationDate, area, population, metersAboveSeaLevel, capital, climate, government, governor.
 */
public class City implements Serializable {
    /** Поле id*/
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /** Поле название*/
    private String name; //Поле не может быть null, Строка не может быть пустой
    /** Поле координаты*/
    private Coordinates coordinates; //Поле не может быть null
    /** Поле дата создания*/
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /** Поле площадь*/
    private Double area; //Значение поля должно быть больше 0, Поле не может быть null
    /** Поле население*/
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    /** Поле метры на уровнем моря*/
    private Integer metersAboveSeaLevel;
    /** Поле столица*/
    private Boolean capital; //Поле может быть null
    /** Поле климат*/
    private Climate climate; //Поле может быть null
    /** Поле правительство*/
    private Government government; //Поле может быть null
    /** Поле губернатор*/
    private Human governor; //Поле может быть null
    private String user;
    /**
     * Функция получения значения поля {@link City#id}
     * @return возвращает id города
     */
    public long getIdOfCity(){
        return id;
    }
    /**
     * Функция получения значения поля {@link City#metersAboveSeaLevel}
     * @return возвращает метры над уровнем моря
     */
    public Integer getMetersAboveSeaLevel(){
        return metersAboveSeaLevel;
    }
    /**
     * Функция переопределения метода toString
     * @return объект в строковом представлении
     */
    @Override
    public String toString() {
        return "City:id=" + this.id + ", name='" + this.name + '\'' + ", coordinates=" + this.coordinates + ", creationDate=" + this.creationDate + ", area=" + this.area +" , population="+this.population+ ", metersAboveSeaLevel=" + this.metersAboveSeaLevel + ", capital=" + this.capital + ", government=" + this.government + ", governor=" + this.governor + ", climate=" + this.climate;
    }
    /**
     * Функция получения значения поля {@link City#population}
     * @return возвращает население города
     */
    public Integer getPopulation(){
        return population;
    }
    /**
     * Функция получения значения поля {@link City#name}
     * @return имя города
     */
    public String getNameCity(){
        return name;
    }

    public Boolean getCapital() {
        return capital;
    }

    public Climate getClimate() {
        return climate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Double getArea() {
        return area;
    }

    public Government getGovernment() {
        return government;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Human getGovernor() {
        return governor;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public void setMetersAboveSeaLevel(Integer metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }
}
