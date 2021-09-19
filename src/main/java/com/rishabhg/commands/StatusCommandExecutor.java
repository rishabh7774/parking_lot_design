package com.rishabhg.commands;

import com.rishabhg.OutputPrinter;
import com.rishabhg.model.Car;
import com.rishabhg.model.Command;
import com.rishabhg.model.Slot;
import com.rishabhg.service.ParkingLotService;

import java.util.List;

/**
 * Executor to handle command of fetching the current status of the parking lot. It gives which
 * slot has which car. Car details will have both its registration number and owner's Age.
 */
public class StatusCommandExecutor extends CommandExecutor {
  public static String COMMAND_NAME = "status";

  public StatusCommandExecutor(final ParkingLotService parkingLotService,
      final OutputPrinter outputPrinter) {
    super(parkingLotService, outputPrinter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validate(final Command command) {
    return command.getParams().isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(Command command) {
    final List<Slot> occupiedSlots = parkingLotService.getOccupiedSlots();

    if (occupiedSlots.isEmpty()) {
      outputPrinter.parkingLotEmpty();
      return;
    }

    outputPrinter.statusHeader();
    for (Slot slot : occupiedSlots) {
      final Car parkedCar = slot.getParkedCar();
      final String slotNumber = slot.getSlotNumber().toString();

      outputPrinter.printWithNewLine(padString(slotNumber, 12)
          + padString(parkedCar.getRegistrationNumber(), 19) + parkedCar.getOwnerAge());
    }
  }

  private static String padString(final String word, final int length) {
    String newWord = word;
    for(int count = word.length(); count < length; count++) {
      newWord = newWord + " ";
    }
    return newWord;
  }
}
