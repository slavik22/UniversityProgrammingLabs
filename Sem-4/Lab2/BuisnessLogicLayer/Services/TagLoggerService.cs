// ***********************************************************************
// Assembly         : BuisnessLogicLayer
// Author           : Slava
// Created          : 12-01-2022
//
// Last Modified By : Slava
// Last Modified On : 12-11-2022
// ***********************************************************************
// <copyright file="TagService.cs" company="BuisnessLogicLayer">
//     Copyright (c) . All rights reserved.
// </copyright>
// <summary></summary>
// ***********************************************************************
using AutoMapper;
using BuisnessLogicLayer.Interfaces;
using BuisnessLogicLayer.Models;
using BuisnessLogicLayer.Validation;
using DataAccessLayer.Entities;
using DataAccessLayer.Interfaces;
using Microsoft.Extensions.Logging;

namespace BuisnessLogicLayer.Services;

/// <summary>
/// Class TagService.
/// Implements the <see cref="ITagService" />
/// </summary>
/// <seealso cref="ITagService" />
public class TagLoggerService
{
    /// <summary>
    /// The unit of work
    /// </summary>
    private readonly IUnitOfWork _unitOfWork;
    /// <summary>
    /// The mapper
    /// </summary>
    private readonly IMapper _mapper;

    private readonly ITagService _tagService;

    private readonly ILogger<TagLoggerService> _logger;

    /// <summary>
    /// Initializes a new instance of the <see cref="TagService" /> class.
    /// </summary>
    /// <param name="unitOfWork">The unit of work.</param>
    /// <param name="mapper">The mapper.</param>
    public TagLoggerService(IUnitOfWork unitOfWork, IMapper mapper, ITagService tagService, ILogger<TagLoggerService> logger)
    {
        _unitOfWork = unitOfWork;
        _mapper = mapper;
        _tagService = tagService;
        _logger = logger;
    }
    /// <summary>
    /// Get all tags
    /// </summary>
    /// <returns></returns>
    public IEnumerable<TagModel> GetAll()
    {
        _logger.LogInformation("Service {Service} Executing {Method}", GetType().Name, nameof(GetAll));
        return _tagService.GetAll();

    }

    /// <summary>
    /// Get tag by id
    /// </summary>
    /// <param name="id"></param>
    /// <returns></returns>
    /// <exception cref="PersonalBlogException"></exception>
    public TagModel? GetById(int id)
    {
        _logger.LogInformation("Service {Service} Executing {Method}", GetType().Name, nameof(GetById));
        return _tagService.GetById(id);
    }
    /// <summary>
    /// Add tag
    /// </summary>
    /// <param name="model"></param>

    public void Add(TagModel model)
    {
        _logger.LogInformation("Service {Service} Executing {Method}", GetType().Name, nameof(Add));
        _tagService.Add(model);
    }

    /// <summary>
    /// Update tag
    /// </summary>
    /// <param name="model"></param>
    public void Update(TagModel model)
    {
        _logger.LogInformation("Service {Service} Executing {Method}", GetType().Name, nameof(Update));
        _tagService.Update(model);

    }
    /// <summary>
    /// Delete tag
    /// </summary>
    /// <param name="modelId"></param>
    public void Delete(int modelId)
    {
        _logger.LogInformation("Service {Service} Executing {Method}", GetType().Name, nameof(Delete));
        _tagService.Delete(modelId);

    }

    /// <summary>
    /// Add tag
    /// </summary>
    /// <param name="postId"></param>
    /// <param name="tagModel"></param>
    /// <exception cref="PersonalBlogException"></exception>
    public async Task AddTag(int postId, TagModel tagModel)
    {
        _logger.LogInformation("Service {Service} Executing {Method}", GetType().Name, nameof(AddTag));
        await _tagService.AddTag(postId, tagModel);
    }

    /// <summary>
    /// Get post's tags
    /// </summary>
    /// <param name="postId"></param>
    /// <returns></returns>
    /// <exception cref="PersonalBlogException"></exception>
    public IEnumerable<TagModel> GetTags(int postId)
    {
        _logger.LogInformation("Service {Service} Executing {Method}", GetType().Name, nameof(GetTags));
        return _tagService.GetTags(postId);
    }
}