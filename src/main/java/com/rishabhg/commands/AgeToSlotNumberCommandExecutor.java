package com.rishabhg.commands;

import com.rishabhg.OutputPrinter;
import com.rishabhg.model.Command;
import com.rishabhg.model.Slot;
import com.rishabhg.service.ParkingLotService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Executor to handle command of fetching all slot numbers in which cars of a particular OwnerAge are
 * parked.
 */
public class AgeToSlotNumberCommandExecutor extends CommandExecutor {
    public static String COMMAND_NAME = "Slot_numbers_for_driver_of_age";

    public AgeToSlotNumberCommandExecutor(
            final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(final Command command) {
        return command.getParams().size() == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final Command command) {
        final List<Slot> slotsForOwnerAge = parkingLotService.getSlotsForOwnerAge(Integer.parseInt(command.getParams().get(0)));
        if (slotsForOwnerAge.isEmpty()) {
            outputPrinter.notFound();
        } else {
            final String result =
                    slotsForOwnerAge.stream()
                            .map(slot -> slot.getSlotNumber().toString())
                            .collect(Collectors.joining(","));
            outputPrinter.printWithNewLine(result);
        }
    }
}
