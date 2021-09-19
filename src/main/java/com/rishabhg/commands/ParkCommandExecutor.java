package com.rishabhg.commands;

import com.rishabhg.OutputPrinter;
import com.rishabhg.exception.NoFreeSlotAvailableException;
import com.rishabhg.model.Car;
import com.rishabhg.model.Command;
import com.rishabhg.service.ParkingLotService;

/**
 * Executor to handle command of parking a car into the parking lot. This outputs the alloted slot
 * in which the car is parked.
 */
public class ParkCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "Park";

  public ParkCommandExecutor(
      final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    return command.getParams().size() == 3;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final Command command) {
    final Car car = new Car(command.getParams().get(0), Integer.parseInt(command.getParams().get(2)));
    try {
      final Integer slot = parkingLotService.park(car);
      outputPrinter.printWithNewLine("Car with vehicle registration number \""+ car.getRegistrationNumber()+"\" has been parked at slot number " + slot);
    } catch (NoFreeSlotAvailableException exception) {
      outputPrinter.parkingLotFull();
    }
  }
}
