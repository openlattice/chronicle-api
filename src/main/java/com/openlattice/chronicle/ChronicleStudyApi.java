package com.openlattice.chronicle;

import com.google.common.collect.SetMultimap;
import com.openlattice.chronicle.sources.Datasource;
import java.util.Optional;
import java.util.UUID;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ChronicleStudyApi {

    String SERVICE    = "/chronicle";
    String CONTROLLER = "/study";
    String BASE       = SERVICE + CONTROLLER;

    String STUDY_ID       = "studyId";
    String PARTICIPANT_ID = "participantId";
    String DATASOURCE_ID  = "datasourceId";
    String ENTITY_SET_ID  = "entitySetId";

    String STUDY_ID_PATH       = "/{" + STUDY_ID + "}";
    String PARTICIPANT_ID_PATH = "/{" + PARTICIPANT_ID + "}";
    String DATASOURCE_ID_PATH  = "/{" + DATASOURCE_ID + "}";
    String ENTITY_SET_ID_PATH  = "/{" + ENTITY_SET_ID + "}";


    /**
     * Enrolls a participant's data datasource in a study. Currently the only supported datasource is an Android device, though
     * though that may change in the future.
     *
     * @apiNote Due to privacy changes in Android the device id is not a reliable way of tracking devices.
     * we are leaving the study path in for now, because we don't know that participant's across studies are unique
     *
     * @param studyId The id of the study with which to enroll the partipant's datasource.
     * @param participantId The participant id which the device will be associated with.
     * @param datasourceId A datasource specific id.
     * @param datasource Datasource specific information.
     *
     * @return The internal chronicle id for a device. It can be used to track a single device across resets, app uninstalls,
     * etc.
     */
    @PUT( BASE + STUDY_ID_PATH + PARTICIPANT_ID_PATH + DATASOURCE_ID_PATH )
    UUID enrollSource(
            @Path( STUDY_ID ) UUID studyId,
            @Path( PARTICIPANT_ID ) UUID participantId,
            @Path( DATASOURCE_ID ) String datasourceId,
            @Body Optional<Datasource> datasource );

    /**
     * Verifies that a participant in a study is associated with a specific data source.
     * @param studyId
     * @param participantId
     * @param datasourceId
     * @return
     */
    //  we are leaving the study path in for now, because we don't know that participant's across studies are unique
    @GET( BASE + STUDY_ID_PATH + PARTICIPANT_ID_PATH + DATASOURCE_ID_PATH )
    Boolean verifyDevice(
            @Path( STUDY_ID ) UUID studyId,
            @Path( PARTICIPANT_ID ) UUID participantId,
            @Path( DATASOURCE_ID ) String datasourceId );

    /**
     * Verify that a participants is part of this study.
     * @param studyId
     * @param participantId
     * @return
     */
    @GET( BASE + STUDY_ID_PATH + PARTICIPANT_ID_PATH )
    Boolean verifyParticipant(
            @Path( STUDY_ID ) UUID studyId,
            @Path( PARTICIPANT_ID ) UUID participantId );
}
