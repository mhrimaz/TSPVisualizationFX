package solvers;

import solvers.model.CityManager;
import solvers.model.Tour;

import java.util.function.Consumer;

@FunctionalInterface
public interface TSPSolver {
    Tour solve(CityManager cityManager, Consumer<Tour> tourConsumer);
}
