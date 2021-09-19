package com.rishabhg.commands;

import com.rishabhg.OutputPrinter;
import com.rishabhg.model.Car;
import com.rishabhg.model.Command;
import com.rishabhg.model.Slot;
import com.rishabhg.service.ParkingLotService;
import com.rishabhg.validator.IntegerValidator;

import java.util.List;
import java.util.Optional;

/**
 * Executor to handle command of freeing of slot from a car.
 */
public class LeaveCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "Leave";

  public LeaveCommandExecutor(final ParkingLotService parkingLotService,
      final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    final List<String> params = command.getParams();
    if (params.size() != 1) {
      return false;
    }
    return IntegerValidator.isInteger(params.get(0));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(final Command command) {
    final int slotNum = Integer.parseInt(command.getParams().get(0));
    final List<Slot> occupiedSlots = parkingLotService.getOccupiedSlots();
    final Optional<Slot> foundSlot = occupiedSlots.stream().filter(slot -> slot.getSlotNumber() == slotNum).findFirst();
    final Car car = foundSlot.get().getParkedCar();
    String regNo = car.getRegistrationNumber();
    int age = car.getOwnerAge();
    parkingLotService.makeSlotFree(slotNum);
    outputPrinter.printWithNewLine("Slot number " + slotNum +
            " vacated, the car with vehicle registration number \""
            + regNo + "\" left the space, the driver of the car was of age "+age);
  }
}
