package com.rishabhg.commands;

import com.rishabhg.OutputPrinter;
import com.rishabhg.model.Command;
import com.rishabhg.model.Slot;
import com.rishabhg.service.ParkingLotService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Executor to handle command of fetching all registration number of cars of a particular ownerAge.
 */
public class AgeToRegNumberCommandExecutor extends CommandExecutor {
    public static String COMMAND_NAME = "Vehicle_registration_number_for_driver_of_age";

    public AgeToRegNumberCommandExecutor(
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
                            .map(slot -> slot.getParkedCar().getRegistrationNumber())
                            .collect(Collectors.joining(", "));
            outputPrinter.printWithNewLine(result);
        }
    }
}
