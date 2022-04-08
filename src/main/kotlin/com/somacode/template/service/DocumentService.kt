package com.somacode.template.service

import com.somacode.template.config.exception.BadRequestException
import com.somacode.template.config.exception.NotFoundException
import com.somacode.template.entity.Document
import com.somacode.template.repository.DocumentRepository
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import javax.transaction.Transactional

@Service
@Transactional
class DocumentService {

    companion object {
        const val STORAGE_PATH = "/static/storage"
    }

    @Autowired lateinit var documentRepository: DocumentRepository


    fun create(multipartFile: MultipartFile, type: Document.Type, tag: String): Document {
        val originalFilename = StringUtils.cleanPath(multipartFile.originalFilename!!)
        val baseName = FilenameUtils.getBaseName(originalFilename)
        val extension = FilenameUtils.getExtension(originalFilename)

        val document = Document()
        documentRepository.save(document)
        document.baseName = baseName
        document.extension = extension
        val ext = if (!extension.isNullOrBlank()) ".$extension" else ""
        val millis = System.currentTimeMillis()
        document.name = "${document.id}D$millis$ext"
        document.description = ""
        document.tag = tag
        document.type = type
        val folder = getFolderFromType(type)
        document.url = "$folder${File.separator}${document.name}"
        saveFile(multipartFile, folder, document.name!!)
        return document
    }

    fun delete(id: Long) {
        val document = findById(id)
        deleteFile(getFolderFromType(document.type!!), document.name!!)
        documentRepository.deleteById(id)
    }

    fun findById(id: Long): Document {
        if (!documentRepository.existsById(id)) {
            throw NotFoundException()
        }
        return documentRepository.getOne(id)
    }

    private fun saveFile(multipartFile: MultipartFile, folder: String, filename: String) {
        if (multipartFile.isEmpty || multipartFile.originalFilename.isNullOrBlank()
                || StringUtils.cleanPath(multipartFile.originalFilename!!).contains("..") || filename.isBlank()) {
            throw BadRequestException()
        }
        val path = Paths.get(getRootPath() + STORAGE_PATH + folder)

        if (!Files.exists(path)) {
            Files.createDirectories(path)
        }

        val resolve = path.resolve(StringUtils.cleanPath(filename))
        Files.copy(multipartFile.inputStream, resolve, StandardCopyOption.REPLACE_EXISTING)
    }

    private fun deleteFile(folder: String, name: String) {
        val path = Paths.get(getRootPath() + STORAGE_PATH + folder)
        val resolve = path.resolve(StringUtils.cleanPath(name))

        if (!Files.exists(path)) {
            throw BadRequestException("Directory doesn't exist")
        } else if (!Files.exists(resolve)) {
            throw BadRequestException("File doesn't exist")
        }
        Files.delete(resolve)
    }

    private fun getRootPath(): String {
        val path = Paths.get("").toAbsolutePath()
        return path.toString().replace("\\", "/")
    }

    private fun getFolderFromType(type: Document.Type): String {
        return "/${type.toString().toLowerCase()}"
    }

}