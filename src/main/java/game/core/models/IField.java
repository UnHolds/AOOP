package game.core.models;

import java.util.List;
import java.util.Map;

public interface IField {

    /**
     * PRE: IField object is not null and has been initialized
     * POST: Returns the number of rows of the IField
     *
     * @return number of rows as an int
     */
    int getRowCount();

    /**
     * PRE: IField object is not null and has been initialized
     * POST: Returns the number of columns of the IField
     *
     * @return number of columns as an int
     */
    int getColumnCount();

    /**
     * PRE: IField object is not null and has been initialized
     * POST: Returns a List of ISubway objects or null
     *
     * @return List of ISubway objects or null
     */
    List<ISubway> getSubways();
}
