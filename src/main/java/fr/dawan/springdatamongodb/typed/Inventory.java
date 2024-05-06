package fr.dawan.springdatamongodb.typed;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Inventory {
    @Id
    private ObjectId id; // ne pas mettre le "_"
    private String item;
    private int qty;
    private List<String> tags;
    private Size size;
    private String status;
}
